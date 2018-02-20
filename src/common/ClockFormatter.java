package common;

import java.util.concurrent.TimeUnit;

public class ClockFormatter {
	
	private ClockFormatter()
	{
		
	}
	
    public static String formatInterval(final long l)
    {
        final long hr = TimeUnit.SECONDS.toHours(l);
        final long min = TimeUnit.SECONDS.toMinutes(l - TimeUnit.HOURS.toSeconds(hr));
        final long sec = TimeUnit.SECONDS.toSeconds(l - TimeUnit.HOURS.toSeconds(hr) - TimeUnit.MINUTES.toSeconds(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}
