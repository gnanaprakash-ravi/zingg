package zingg;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.RelationalGroupedDataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.functions;
import org.apache.spark.sql.catalyst.encoders.RowEncoder;
import org.apache.spark.sql.expressions.Window;
import org.apache.spark.sql.expressions.WindowSpec;

import scala.collection.JavaConverters;
import zingg.block.Block;
import zingg.block.Canopy;
import zingg.block.Tree;
import zingg.model.Model;
import zingg.client.ZFrame;
import zingg.client.ZinggClientException;
import zingg.client.ZinggOptions;
import zingg.client.util.ColName;
import zingg.client.util.ColValues;
import zingg.client.util.Util;
import zingg.util.DSUtil;
import zingg.util.GraphUtil;
import zingg.util.ModelUtil;
import zingg.util.PipeUtilBase;

import zingg.scala.TypeTags;
import zingg.spark.util.BlockingTreeUtil;
import zingg.scala.DFUtil;

public abstract class Linker<S,D,R,C,T1,T2> extends Matcher<S,D,R,C,T1,T2> {

	protected static String name = "zingg.Linker";
	public static final Log LOG = LogFactory.getLog(Linker.class);

	public Linker() {
		setZinggOptions(ZinggOptions.LINK);
	}

	protected ZFrame<D,R,C> getBlocks(ZFrame<D,R,C> blocked, ZFrame<D,R,C> bAll) throws Exception{
		return getDSUtil().joinWithItselfSourceSensitive(blocked, ColName.HASH_COL, args).cache();
	}

	protected ZFrame<D,R,C> selectColsFromBlocked(ZFrame<D,R,C> blocked) {
		return blocked;
	}

	public void writeOutput(ZFrame<D,R,C> blocked, ZFrame<D,R,C> dupes) {
		try {
			// input dupes are pairs
			/// pick ones according to the threshold by user
			ZFrame<D,R,C> dupesActual = getDupesActualForGraph(dupes);

			// all clusters consolidated in one place
			if (args.getOutput() != null) {

				// input dupes are pairs
				//dupesActual = DFUtil.addClusterRowNumber(dupesActual, spark);
				dupesActual = getDSUtil().addUniqueCol(dupesActual, ColName.ID_COL);
				ZFrame<D,R,C> dupes2 = getDSUtil().alignLinked(dupesActual, args);
				//LOG.debug("uncertain output schema is " + dupes2.schema());
				getPipeUtil().write(dupes2, args, args.getOutput());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected ZFrame<D,R,C> getDupesActualForGraph(ZFrame<D,R,C> dupes) {
		ZFrame<D,R,C> dupesActual = dupes
				.filter(dupes.equalTo(ColName.PREDICTION_COL, ColValues.IS_MATCH_PREDICTION));
		return dupesActual;
	}

	

}
