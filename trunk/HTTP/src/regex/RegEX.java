package regex;

import java.util.regex.*;

public class RegEX
{
	private String s = "";
	
	public String findInURL(String t, Pattern p)
	{
		Matcher m = p.matcher(t);
		
		if (m.find())
			s = m.group(0);
		
		return s.toString();
	}
}