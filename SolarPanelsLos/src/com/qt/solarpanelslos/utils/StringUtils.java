package com.qt.solarpanelslos.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String Utils
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2011-7-22
 */
public class StringUtils {

    private StringUtils() {
        throw new AssertionError();
    }

    /**
     * is null or its length is 0 or it is made by space
     * 
     * <pre>
     * isBlank(null) = true;
     * isBlank(&quot;&quot;) = true;
     * isBlank(&quot;  &quot;) = true;
     * isBlank(&quot;a&quot;) = false;
     * isBlank(&quot;a &quot;) = false;
     * isBlank(&quot; a&quot;) = false;
     * isBlank(&quot;a b&quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0 or it is made by space, return true, else return false.
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }

    /**
     * is null or its length is 0
     * 
     * <pre>
     * isEmpty(null) = true;
     * isEmpty(&quot;&quot;) = true;
     * isEmpty(&quot;  &quot;) = false;
     * </pre>
     * 
     * @param str
     * @return if string is null or its size is 0, return true, else return false.
     */
    public static boolean isEmpty(CharSequence str) {
        return (str == null || str.length() == 0);
    }

    /**
     * compare two string
     * 
     * @param actual
     * @param expected
     * @return
     * @see ObjectUtils#isEquals(Object, Object)
     */
    public static boolean isEquals(String actual, String expected) {
        return ObjectUtils.isEquals(actual, expected);
    }

    /**
     * get length of CharSequence
     * 
     * <pre>
     * length(null) = 0;
     * length(\"\") = 0;
     * length(\"abc\") = 3;
     * </pre>
     * 
     * @param str
     * @return if str is null or empty, return 0, else return {@link CharSequence#length()}.
     */
    public static int length(CharSequence str) {
        return str == null ? 0 : str.length();
    }

    /**
     * null Object to empty string
     * 
     * <pre>
     * nullStrToEmpty(null) = &quot;&quot;;
     * nullStrToEmpty(&quot;&quot;) = &quot;&quot;;
     * nullStrToEmpty(&quot;aa&quot;) = &quot;aa&quot;;
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String nullStrToEmpty(Object str) {
        return (str == null ? "" : (str instanceof String ? (String)str : str.toString()));
    }

    /**
     * capitalize first letter
     * 
     * <pre>
     * capitalizeFirstLetter(null)     =   null;
     * capitalizeFirstLetter("")       =   "";
     * capitalizeFirstLetter("2ab")    =   "2ab"
     * capitalizeFirstLetter("a")      =   "A"
     * capitalizeFirstLetter("ab")     =   "Ab"
     * capitalizeFirstLetter("Abc")    =   "Abc"
     * </pre>
     * 
     * @param str
     * @return
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }

        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str : new StringBuilder(str.length())
                .append(Character.toUpperCase(c)).append(str.substring(1)).toString();
    }

    /**
     * encoded in utf-8
     * 
     * <pre>
     * utf8Encode(null)        =   null
     * utf8Encode("")          =   "";
     * utf8Encode("aa")        =   "aa";
     * utf8Encode("啊啊啊啊")   = "%E5%95%8A%E5%95%8A%E5%95%8A%E5%95%8A";
     * </pre>
     * 
     * @param str
     * @return
     * @throws UnsupportedEncodingException if an error occurs
     */
    public static String utf8Encode(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }

    /**
     * encoded in utf-8, if exception, return defultReturn
     * 
     * @param str
     * @param defultReturn
     * @return
     */
    public static String utf8Encode(String str, String defultReturn) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return defultReturn;
            }
        }
        return str;
    }

    /**
     * get innerHtml from href
     * 
     * <pre>
     * getHrefInnerHtml(null)                                  = ""
     * getHrefInnerHtml("")                                    = ""
     * getHrefInnerHtml("mp3")                                 = "mp3";
     * getHrefInnerHtml("&lt;a innerHtml&lt;/a&gt;")                    = "&lt;a innerHtml&lt;/a&gt;";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a&lt;a&gt;innerHtml&lt;/a&gt;")                    = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com"&gt;innerHtml&lt;/a&gt;")               = "innerHtml";
     * getHrefInnerHtml("&lt;a href="baidu.com" title="baidu"&gt;innerHtml&lt;/a&gt;") = "innerHtml";
     * getHrefInnerHtml("   &lt;a&gt;innerHtml&lt;/a&gt;  ")                           = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                      = "innerHtml";
     * getHrefInnerHtml("jack&lt;a&gt;innerHtml&lt;/a&gt;&lt;/a&gt;")                  = "innerHtml";
     * getHrefInnerHtml("&lt;a&gt;innerHtml1&lt;/a&gt;&lt;a&gt;innerHtml2&lt;/a&gt;")        = "innerHtml2";
     * </pre>
     * 
     * @param href
     * @return <ul>
     *         <li>if href is null, return ""</li>
     *         <li>if not match regx, return source</li>
     *         <li>return the last string that match regx</li>
     *         </ul>
     */
    public static String getHrefInnerHtml(String href) {
        if (isEmpty(href)) {
            return "";
        }

        String hrefReg = ".*<[\\s]*a[\\s]*.*>(.+?)<[\\s]*/a[\\s]*>.*";
        Pattern hrefPattern = Pattern.compile(hrefReg, Pattern.CASE_INSENSITIVE);
        Matcher hrefMatcher = hrefPattern.matcher(href);
        if (hrefMatcher.matches()) {
            return hrefMatcher.group(1);
        }
        return href;
    }

/**
     * process special char in html
     * 
     * <pre>
     * htmlEscapeCharsToString(null) = null;
     * htmlEscapeCharsToString("") = "";
     * htmlEscapeCharsToString("mp3") = "mp3";
     * htmlEscapeCharsToString("mp3&lt;") = "mp3<";
     * htmlEscapeCharsToString("mp3&gt;") = "mp3\>";
     * htmlEscapeCharsToString("mp3&amp;mp4") = "mp3&mp4";
     * htmlEscapeCharsToString("mp3&quot;mp4") = "mp3\"mp4";
     * htmlEscapeCharsToString("mp3&lt;&gt;&amp;&quot;mp4") = "mp3\<\>&\"mp4";
     * </pre>
     * 
     * @param source
     * @return
     */
    public static String htmlEscapeCharsToString(String source) {
        return StringUtils.isEmpty(source) ? source : source.replaceAll("&lt;", "<").replaceAll("&gt;", ">")
                .replaceAll("&amp;", "&").replaceAll("&quot;", "\"");
    }

    /**
     * transform half width char to full width char
     * 
     * <pre>
     * fullWidthToHalfWidth(null) = null;
     * fullWidthToHalfWidth("") = "";
     * fullWidthToHalfWidth(new String(new char[] {12288})) = " ";
     * fullWidthToHalfWidth("！＂＃＄％＆) = "!\"#$%&";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String fullWidthToHalfWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == 12288) {
                source[i] = ' ';
                // } else if (source[i] == 12290) {
                // source[i] = '.';
            } else if (source[i] >= 65281 && source[i] <= 65374) {
                source[i] = (char)(source[i] - 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }

    /**
     * transform full width char to half width char
     * 
     * <pre>
     * halfWidthToFullWidth(null) = null;
     * halfWidthToFullWidth("") = "";
     * halfWidthToFullWidth(" ") = new String(new char[] {12288});
     * halfWidthToFullWidth("!\"#$%&) = "！＂＃＄％＆";
     * </pre>
     * 
     * @param s
     * @return
     */
    public static String halfWidthToFullWidth(String s) {
        if (isEmpty(s)) {
            return s;
        }

        char[] source = s.toCharArray();
        for (int i = 0; i < source.length; i++) {
            if (source[i] == ' ') {
                source[i] = (char)12288;
                // } else if (source[i] == '.') {
                // source[i] = (char)12290;
            } else if (source[i] >= 33 && source[i] <= 126) {
                source[i] = (char)(source[i] + 65248);
            } else {
                source[i] = source[i];
            }
        }
        return new String(source);
    }
    
/*	// 防止xss bug
	public static String xssEncode(String s) {
		if (s == null || s.isEmpty()) {
			return s;
		}
		StringBuilder sb = new StringBuilder(s.length() + 16);
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			switch (c) {
			case '>':
				sb.append('＞');// 全角大于号
				break;
			case '<':
				sb.append('＜');// 全角小于号
				break;
			case '\'':
				sb.append('‘');// 全角单引号
				break;
			case '\"':
				sb.append('“');// 全角双引号
				break;
			case '&':
				sb.append('＆');// 全角
				break;
			case '\\':
				sb.append('＼');// 全角斜线
				break;
			case '#':
				sb.append('＃');// 全角井号
				break;
			default:
				sb.append(c);
				break;
			}
		}
		return sb.toString();
	}*/
	
	/**
	 * 
	 * @param str1
	 * @param str2
	 * @return all blank or all not blank && equals
	 */
	public static boolean isSame(String str1, String str2) {
		// all blank
		if (StringUtils.isBlank(str1) && StringUtils.isBlank(str2)) {
			return true;
		}
		// ALL not blank && equals
		if (!StringUtils.isBlank(str1) && !StringUtils.isBlank(str2) && str1.equals(str2)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否是电话号码
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isPhone(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 判断字符串是否是邮箱
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isEmailAddress(String str) {
		Pattern pattern = Pattern.compile("^([0-9a-zA-Z\\._-])+@([0-9a-zA-Z]+\\.)+([a-zA-Z])+$");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 验证是否是手机号码
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isMobile(String str) {
		Pattern pattern = Pattern.compile("1[0-9]{10}");
		Matcher matcher = pattern.matcher(str);
		if (matcher.matches()) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * 判断字符串是否是数字和字母
	 * 
	 * @param str
	 * @return true or false
	 */
	public static boolean isDigitChar(String str) {
		Pattern pattern = Pattern.compile("([0-9a-zA-Z])+");
		Matcher matcher = pattern.matcher(str);
		return matcher.matches();
	}

	/**
	 * 将返回的距离结果数据，优化成显示数据
	 * 
	 * @param distance
	 * @return
	 */
	public static String parseDistance(String distance) {
		String result = "";
		if (distance != null) {
			Float dis = Float.valueOf(distance) * 1000;
			result = String.valueOf(dis);
			result = result.substring(0, result.indexOf(".")) + "米";
		}
		return result;
	}

	/**
	 * 校验有效电话号码
	 * 
	 * @param telNumber
	 * @return
	 */
	public static String getValidTelNumber(String telNumber) {
		String str = telNumber.replace(" ", "");
		str = str.replace("-", "");
		str = str.replace("+86", "");
		Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		return m.matches() ? str : null;
	}

	/**
	 * 去掉字符串中标点符号及其他特殊符号
	 * 
	 * @param
	 * @return String
	 */
	public static String format(String s) {
		String str = s.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
		return str;
	}
	
	/**
	 * 计算中英文混合字符串的长度
	 * @param name
	 * @param endcoding
	 * @return
	 * @throws Exception
	 */
	public static int getChineseLength(String name , String endcoding )
            throws Exception{
		if(name == null || name.trim().length() == 0){
			return 0;
		}
        int len = 0 ; //定义返回的字符串长度
        int j = 0 ;
        //按照指定编码得到byte[]
        byte [] b_name = name.getBytes( endcoding ) ;
        while ( true ){
            short tmpst = (short) ( b_name[ j ] & 0xF0 ) ;
            if ( tmpst >= 0xB0 ){
                if ( tmpst < 0xC0 ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( ( tmpst == 0xC0 ) || ( tmpst == 0xD0 ) ){
                    j += 2 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xE0 ){
                    j += 3 ;
                    len += 2 ;
                }
                else if ( tmpst == 0xF0 ){
                    short tmpst0 = (short) ( ( (short) b_name[ j ] ) & 0x0F ) ;
                    if ( tmpst0 == 0 ){
                        j += 4 ;
                        len += 2 ;
                    }
                    else if ( ( tmpst0 > 0 ) && ( tmpst0 < 12 ) ){
                        j += 5 ;
                        len += 2 ;
                    }
                    else if ( tmpst0 > 11 ){
                        j += 6 ;
                        len += 2 ;
                    }
                }
            }
            else{
                j += 1 ;
                len += 1 ;
            }
            if ( j > b_name.length - 1 ){
                break ;
            }
        }
        return len ;
    }
	
	/**
	 * 含有汉子的字符串，获取指定ASCII码长度的子字符串长度
	 * @param name
	 * @param endcoding
	 * @param length
	 * @return
	 * @throws Exception
	 */
	public static int getChineseStringSubLenByAsciiLen(String name , String endcoding , int length) throws Exception{
		if(name == null || name.trim().equals("")){
			return 0;
		}
		int len = 0 ; //定义返回的字符串长度
        int j = 0 ;
        int i = 0;
        //按照指定编码得到byte[]
        byte [] b_name = name.getBytes( endcoding ) ;
        while ( true ){
            short tmpst = (short) ( b_name[ j ] & 0xF0 ) ;
            if ( tmpst >= 0xB0 ){
                if ( tmpst < 0xC0 ){
                    j += 2 ;
                    len += 2 ;
                    i ++;
                }
                else if ( ( tmpst == 0xC0 ) || ( tmpst == 0xD0 ) ){
                    j += 2 ;
                    len += 2 ;
                    i ++ ;
                }
                else if ( tmpst == 0xE0 ){
                    j += 3 ;
                    len += 2 ;
                    i++;
                }
                else if ( tmpst == 0xF0 ){
                    short tmpst0 = (short) ( ( (short) b_name[ j ] ) & 0x0F ) ;
                    if ( tmpst0 == 0 ){
                        j += 4 ;
                        len += 2 ;
                        i++;
                    }
                    else if ( ( tmpst0 > 0 ) && ( tmpst0 < 12 ) ){
                        j += 5 ;
                        len += 2 ;
                        i++;
                    }
                    else if ( tmpst0 > 11 ){
                        j += 6 ;
                        len += 2 ;
                        i++;
                    }
                }
            }
            else{
                j += 1 ;
                len += 1 ;
                i++;
            }
            if ( j > b_name.length - 1 ){
                break ;
            }
            if(len == length){
            	break;
            }
            else if(len > length){
            	i -- ;
            	break ;
            }
        }
        return i ;
	}
	
	/**
	 * 位数不足，前置位补 0
	 * @param value
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public static String zeroFillByZero(String value , int len) throws Exception{
		if(value == null || len == 0){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		int strLength = getChineseLength(value, "utf-8");
		if(strLength < len){
			int zeroNum = len - strLength ;
			for(int i = 0; i < zeroNum ; i ++){
				builder.append("0");
			}
			builder.append(value);
		}
		else{
			builder.append(value.substring(0, len));
		}
		return builder.toString() ;
	}
	
	public static String zeroFillByZero(int value , int len) throws Exception{
		return zeroFillByZero(Integer.toString(value), len);
	}
	
	public static String zeroFillByZero(float value , int len) throws Exception{
		return zeroFillByZero(Float.toString(value), len);
	}
	
	/**
	 * 位数不足，后置位补空格
	 * @param value
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public static String zeroFillBySpace(String value, int len) throws Exception{
		if(value == null || len == 0){
			return "";
		}
		StringBuilder builder = new StringBuilder();
		int strLength = getChineseLength(value, "utf-8");
		if(strLength < len){
			builder.append(value);
			int spaceNum = len - strLength ;
			for(int i = 0 ; i < spaceNum ; i ++){
				builder.append(" ");
			}
		}
		else{
			int subLen = getChineseStringSubLenByAsciiLen(value, "utf-8", len);
			builder.append(value.substring(0, subLen));
		}
		return builder.toString() ;
	}
}
