package egova.com.cn.environment.util;

public enum SpecialChar {
	/** 位与符“&”. */
	Egova_1("*#egova_1#*"),
	/** 左尖括号“<”. */
	Egova_2("*#egova_2#*"),
	/** 右尖括号“>”. */
	Egova_3("*#egova_3#*"),  
	/** 双引号“"”. */
	Egova_4("*#egova_4#*"),  
	/** 单引号“'”. */
	Egova_5("*#egova_5#*"), 
	/** 分号“;”. */
	Egova_6("*#egova_6#*"),  
	/** 逗号“,”.. */
	Egova_7("*#egova_7#*"),  
	/** 左括号“(”. */
	Egova_8("*#egova_8#*"),  
	/** 右括号“)”. */
	Egova_9("*#egova_9#*"),
	
	/** 位与符“&”. */
	Char_1("&amp;"),
	/** 左尖括号“<”. */
	Char_2("&lt;"),
	/** 右尖括号“>”. */
	Char_3("&gt;"),  
	/** 双引号“"”. */
	Char_4("&quot;"),  
	/** 单引号“'”. */
	Char_5("&apos;")
	;
	
	private String dirStr = "";
	SpecialChar(String dirStr) {
		this.dirStr = dirStr;
	}
	
	@Override
	public String toString() {
		return dirStr;
	}
}
