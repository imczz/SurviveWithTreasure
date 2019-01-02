package czz.compilation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * token序列
 * @author CZZ
 * */
public class tokenlist {
	
	/**
	 * 标识符（identifier）
	 * */
	HashMap<String, token> iT;
	
	/**
	 * 字符（character）
	 * */
	HashMap<String, token> cT;
	
	/**
	 * 字符串（string）
	 * */
	HashMap<String, token> sT;
	
	/**
	 * 常数（constant）
	 * */
	HashMap<String, token> CT;
	
	/**
	 * token序列
	 * */
	private ArrayList<token> tk;
	
	/**
	 * 被承认的界符（用来决定是否进入PT自动机）
	 * */
	static HashSet<Character> strtable;
	
	/**
	 * 关键字（keyword）
	 * */
	static HashMap<String, token> KT;
	
	/**
	 * 界符（delimiter）
	 * */
	static HashMap<String, token> PT;

	/*================================方法 methods================================*/
	
	static {
		KT = new HashMap<String, token>();			//关键字   	4
		PT=new HashMap<String, token>();				//界符		5
		strtable=new HashSet<Character>();	//承认的界符
		
		strtable.add('<');
		strtable.add('>');
		strtable.add('=');
		strtable.add('!');
		strtable.add('|');
		strtable.add('&');
		strtable.add('+');
		strtable.add('-');
		strtable.add('*');
		strtable.add('/');
		strtable.add(':');
		strtable.add('(');
		strtable.add(')');
		strtable.add('[');
		strtable.add(']');
		strtable.add('{');
		strtable.add('}');
		strtable.add(';');
		strtable.add(',');
		strtable.add('^');
		strtable.add('%');
		strtable.add('?');
		strtable.add('.');
		strtable.add('#');
		//strtable.add('\'');
		//strtable.add('\"');
		
		KT.put("asm", new token(4, 0, "asm"));						//C++的63个标识符
		KT.put("auto", new token(4, 1, "auto"));
		KT.put("bool", new token(4, 2, "bool"));
		KT.put("break", new token(4, 3, "break"));
		KT.put("case", new token(4, 4, "case"));
		KT.put("catch", new token(4, 5, "catch"));
		KT.put("char", new token(4, 6, "char"));
		KT.put("class", new token(4, 7, "class"));
		KT.put("const", new token(4, 8, "const"));
		KT.put("const_cast", new token(4, 9, "const_cast"));
		KT.put("continue", new token(4, 10, "continue"));
		KT.put("default", new token(4, 11, "default"));
		KT.put("delete", new token(4, 12, "delete"));
		KT.put("do", new token(4, 13, "do"));
		KT.put("double", new token(4, 14, "double"));
		KT.put("dynamic_cast", new token(4, 15, "dynamic_cast"));
		KT.put("else", new token(4, 16, "else"));
		KT.put("enum", new token(4, 17, "enum"));
		KT.put("explicit", new token(4, 18, "explicit"));
		KT.put("export", new token(4, 19, "export"));
		KT.put("extern", new token(4, 20, "extern"));
		KT.put("false", new token(4, 21, "false"));
		KT.put("float", new token(4, 22, "float"));
		KT.put("for", new token(4, 23, "for"));
		KT.put("friend", new token(4, 24, "friend"));
		KT.put("goto", new token(4, 25, "goto"));
		KT.put("if", new token(4, 26, "if"));
		KT.put("inline", new token(4, 27, "inline"));
		KT.put("int", new token(4, 28, "int"));
		KT.put("long", new token(4, 29, "long"));
		KT.put("mutable", new token(4, 30, "mutable"));
		KT.put("namespace", new token(4, 31, "namespace"));
		KT.put("new", new token(4, 32, "new"));
		KT.put("operator", new token(4, 33, "operator"));
		KT.put("private", new token(4, 34, "private"));
		KT.put("protected", new token(4, 35, "protected"));
		KT.put("public", new token(4, 36, "public"));
		KT.put("register", new token(4, 37, "register"));
		KT.put("reinterpret_cast", new token(4, 38, "reinterpret_cast"));
		KT.put("return", new token(4, 39, "return"));
		KT.put("short", new token(4, 40, "short"));
		KT.put("signed", new token(4, 41, "signed"));
		KT.put("sizeof", new token(4, 42, "sizeof"));
		KT.put("static", new token(4, 43, "static"));
		KT.put("static_cast", new token(4, 44, "static_cast"));
		KT.put("struct", new token(4, 45, "struct"));
		KT.put("switch", new token(4, 46, "switch"));
		KT.put("template", new token(4, 47, "template"));
		KT.put("this", new token(4, 48, "this"));
		KT.put("throw", new token(4, 49, "throw"));
		KT.put("true", new token(4, 50, "true"));
		KT.put("try", new token(4, 51, "try"));
		KT.put("typedef", new token(4, 52, "typedef"));
		KT.put("typeid", new token(4, 53, "typeid"));
		KT.put("typename", new token(4, 54, "typename"));
		KT.put("union", new token(4, 55, "union"));
		KT.put("unsigned", new token(4, 56, "unsigned"));
		KT.put("using", new token(4, 57, "using"));
		KT.put("virtual", new token(4, 58, "virtual"));
		KT.put("void", new token(4, 59, "void"));
		KT.put("volatile", new token(4, 60, "volatile"));
		KT.put("wchar_t", new token(4, 61, "wchar_t"));
		KT.put("while", new token(4, 62, "while"));
		
		PT.put(">", new token(5, 0, ">"));								//c++的界符（可能不完整）
		PT.put(">=", new token(5, 1, ">="));
		PT.put(">>", new token(5, 2, ">>"));
		PT.put("<", new token(5, 3, "<"));
		PT.put("<=", new token(5, 4, "<="));
		PT.put("<<", new token(5, 5, "<<"));
		PT.put("=", new token(5, 6, "="));
		PT.put("==", new token(5, 7, "=="));
		PT.put("!", new token(5, 8, "!"));
		PT.put("!=", new token(5, 9, "!="));
		PT.put("|", new token(5, 10, "|"));
		PT.put("||", new token(5, 11, "||"));
		PT.put("&", new token(5, 12, "&"));
		PT.put("&&", new token(5, 13, "&&"));
		PT.put("+", new token(5, 14, "+"));
		PT.put("++", new token(5, 15, "++"));
		PT.put("-", new token(5, 16, "-"));
		PT.put("--", new token(5, 17, "--"));
		PT.put("->", new token(5, 18, "->"));
		PT.put(":", new token(5, 19, ":"));
		PT.put("::", new token(5, 20, "::"));
		PT.put("*", new token(5, 21, "*"));
		PT.put("/", new token(5, 22, "/"));
		PT.put("%", new token(5, 23, "%"));
		PT.put("^", new token(5, 24, "^"));
		PT.put("(", new token(5, 25, "("));
		PT.put(")", new token(5, 26, ")"));
		PT.put("[", new token(5, 27, "["));
		PT.put("]", new token(5, 28, "]"));
		PT.put("{", new token(5, 29, "{"));
		PT.put("}", new token(5, 30, "}"));
		PT.put(";", new token(5, 31, ";"));
		PT.put(",", new token(5, 32, ","));
		PT.put(".", new token(5, 33, "."));
		PT.put("?", new token(5, 34, "?"));
		PT.put("#", new token(5, 35, "#"));
	}
	
	/**
	 * 构造方法
	 * */
	tokenlist(){
		this.iT = new HashMap<String, token>();			//标识符		0
		this.cT = new HashMap<String, token>();			//字符		1
		this.sT = new HashMap<String, token>();			//字符串		2
		this.CT = new HashMap<String, token>();			//常数		3
		this.tk = new ArrayList<token>();
	}
	
	/**
	 * 将token加入token序列，同时补充各个序列的值
	 * @param tokenClass token的类别
	 * @param buffer 字符串缓冲区
	 * @return true 添加成功;false 添加失败
	 * */
	public boolean addtoken(String tokenClass, StringBuffer buffer){				//把字符串装进对应名字的token序列中
		boolean ret = false;
		token tmpToken = null;							//临时的token
		String tmpstr = buffer.toString();			//buffer中存放的字符串
		StringBuffer strbuff = new StringBuffer(buffer);		//用来切割字符串
		if(tokenClass == "KTiT"){
			tmpToken = KT.get(tmpstr);
			if (tmpToken == null) tmpToken = this.iT.get(tmpstr);			//不为关键字（或者为保留字）
			if (tmpToken == null) {											//未定义的标识符
				tmpToken = new token(0, this.iT.size(), tmpstr);		//新的标识符
				this.iT.put(tmpstr, tmpToken);
			}
		}
		else if(tokenClass == "CT"){					//常数
			tmpToken = this.CT.get(tmpstr);
			if (tmpToken == null) {
				tmpToken = token.StrToNum(tmpstr);				//新的常数
				tmpToken.setIndex2(this.CT.size());
				this.CT.put(tmpstr, tmpToken);
			}
		}
		else if(tokenClass == "cT"){					//字符
			tmpToken = this.cT.get(tmpstr);
			if (tmpToken == null) {
				tmpToken = new token(1, this.cT.size(), tmpstr);		//新的字符
				tmpToken.ch = tmpstr.charAt(1);
				this.cT.put(tmpstr, tmpToken);
			}
		}
		else if(tokenClass == "sT"){					//字符串
			strbuff.deleteCharAt(strbuff.length() - 1);			//删除字符串前的引号
			strbuff.deleteCharAt(0);							//删除字符串后的引号
			tmpstr = strbuff.toString();
			tmpToken = this.cT.get(tmpstr);
			if (tmpToken == null) {
				tmpToken = new token(2, sT.size(), tmpstr);		//新的字符串
				sT.put(tmpstr, tmpToken);
			}
		}
		else if(tokenClass == "PT"){					//界符
			tmpToken = PT.get(tmpstr);
		}
		else{											//不正确的token类别
			tmpToken = null;
		}
		if (tmpToken != null) {
			this.tk.add(tmpToken);
			ret = true;
		}
		return ret;
	}
	
	public void output(){					//控制台一一对应输出已经成成的token序列
		int in1, in2;
		for(int i = 0; i < this.tk.size(); i++)
		{
			System.out.print("<");
			in1=this.tk.get(i).getIndex1();
			System.out.print(in1 + ",");
			in2=this.tk.get(i).getIndex2();
			if(in2<10) System.out.print("0");
			System.out.print(in2 + "> ");
			if(in1==3)
			{
				if(this.tk.get(i).getNum1()==0) System.out.print(this.tk.get(i).getNum2());
				else System.out.print(this.tk.get(i).getNum1());
			}
			else if(in1==1){
				System.out.print(this.tk.get(i).ch);
			}
			else{
				System.out.print(this.tk.get(i).getStr());
			}
			System.out.println();
		}
	}

	public ArrayList<token> getTk() {
		return tk;
	}

	public void setTk(ArrayList<token> tk) {
		this.tk = tk;
	}
}
