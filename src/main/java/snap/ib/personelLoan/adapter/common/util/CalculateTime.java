package snap.ib.personelLoan.adapter.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.ib.personelLoan.adapter.common.CommonAdapterResource;

public class CalculateTime{

	private static Logger logger = LoggerFactory.getLogger(CommonAdapterResource.class);
	public static Long getTimeInSeconds(Long previousTime,Long currentTime) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Date previousDate = new Date(previousTime);
		String previousTime2 = sdf.format(previousDate);
		
		Date currentDate = new Date(currentTime);
		String currentTime2 = sdf.format(currentDate);

		Date previousDate2 = sdf.parse(previousTime2);
		logger.info("previousDate: "+previousDate2);
		
		Date currentDate2 = sdf.parse(currentTime2);
		logger.info("currentDate: "+currentDate2);
		
		long difference = currentDate2.getTime() - previousDate2.getTime(); 
		difference=difference/1000;
		
		logger.info("time difference"+difference);
		return Long.valueOf(difference);
	}

}
