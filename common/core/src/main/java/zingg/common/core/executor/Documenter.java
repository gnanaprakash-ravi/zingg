package zingg.common.core.executor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zingg.common.client.ZinggClientException;
import zingg.common.client.ZinggOptions;
import zingg.common.client.ZFrame;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;
import zingg.common.client.FieldDefinition;
import zingg.common.client.MatchType;
import zingg.common.client.ZinggClientException;
import zingg.common.client.ZinggOptions;
import zingg.common.client.util.ColName;
import zingg.common.core.documenter.DataDocumenter;
import zingg.common.core.documenter.ModelDocumenter;
import zingg.common.core.util.DSUtil;
import zingg.common.core.util.PipeUtilBase;
import zingg.common.core.util.RowAdapter;
import zingg.common.core.util.RowWrapper;

public abstract class Documenter<S,D,R,C,T> extends ZinggBase<S,D,R,C,T> {

	protected static String name = "zingg.Documenter";
	public static final Log LOG = LogFactory.getLog(Documenter.class);

	public Documenter() {
		setZinggOptions(ZinggOptions.GENERATE_DOCS);
 	}

	public void execute() throws ZinggClientException {
		try {
			LOG.info("Documenter starts");
			//Documentation out of model
			/*ModelDocumenter modelDoc = new ModelDocumenter(args);
			modelDoc.process();

			//Documnetation of data
			DataDocumenter dataDoc = new DataDocumenter(args);
			dataDoc.process();
			*/

			LOG.info("Documenter finishes");
		} catch (Exception e) {
			e.printStackTrace();
			throw new ZinggClientException(e.getMessage());
		}
	}
}