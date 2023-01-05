package zingg.spark.hash.unused;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import zingg.spark.hash.SparkTrimLastDigitsDbl;

/**
 * Spark specific trim function for double last 2 digits
 * 
 * 
 * @author vikasgupta
 *
 */
public class SparkTrimLast2DigitsDbl extends SparkTrimLastDigitsDbl {
	
	public static final Log LOG = LogFactory.getLog(SparkTrimLast2DigitsDbl.class);

	public SparkTrimLast2DigitsDbl(){
	    super(2);
	}
	
}
