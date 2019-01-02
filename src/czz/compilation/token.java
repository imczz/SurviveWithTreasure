package czz.compilation;

/**
 * 单个token,采用2级结构
 * @author CZZ
 * */
public class token {
	
	/**
	 * 一级标签（类别）
	 * */
	private int index1;
	
	/**
	 * 二级标签（具体的分类）
	 * */
	private int index2;
	
	/**
	 * 标签的原始文本
	 * */
	private String str;
	
	/**
	 * 如果token内容是一个字符
	 * */
	char ch;				//如果是字符
	
	/**
	 * 不为零则自身为浮点数，为0则查看num2的值
	 * */
	private double num1;
	
	/**
	 * 如果num1为0.则此token表示一个整数
	 * */
	private int num2;
	
	/*================================方法 methods================================*/
	
	public int getIndex1() {
		return index1;
	}

	public void setIndex1(int index1) {
		this.index1 = index1;
	}

	public int getIndex2() {
		return index2;
	}

	public void setIndex2(int index2) {
		this.index2 = index2;
	}
	
	public double getNum1() {
		return num1;
	}

	public void setNum1(double num1) {
		this.num1 = num1;
	}

	public int getNum2() {
		return num2;
	}

	public void setNum2(int num2) {
		this.num2 = num2;
	}


	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}
	
	/**
	 * 构造方法
	 * */
	token(){
		setIndex1(0);
		setIndex2(0);
		ch='\0';
		setNum1(0);
		setNum2(0);
	}
	
	/**
	 * 构造方法2
	 * */
	public token(int index1, int index2, String str){				//简易的构造方法
		setIndex1(index1);
		setIndex2(index2);
		this.setStr(str);
		this.ch='\0';
		setNum1(0);
		setNum2(0);
	}
	
	/**
	 * 构造方法3
	 * */
	public token(int index1, int index2, String str, char c, double num1, int num2){		//复杂的构造方法
		setIndex1(index1);
		setIndex2(index2);
		this.setStr(str);
		this.ch=c;
		setNum1(num1);
		setNum2(num2);
	}
	
	/**
	 * 复制（拷贝）构造方法
	 * */
	token(token t1){
		setIndex1(t1.getIndex1());
		setIndex2(t1.getIndex2());
		setStr(t1.getStr());
		this.ch=t1.ch;
		setNum1(t1.getNum1());
		setNum2(t1.getNum2());
	}
	
	/**
	 * 比较两个token
	 * @param t1 另一个token
	 * @return true 两个token相同;false 两个token不同
	 * */
	public boolean equals(token t1){
		if(getIndex1()==t1.getIndex1() && getIndex2()==t1.getIndex2() && getStr()==t1.getStr()) return true;
		else return false;
	}
	
	/**
	 * 比较两个token的来源字符串
	 * @param t1 另一个token
	 * @return true 两个token的来源字符串相同;false 两个token的来源字符串不同
	 * */
	public boolean equalstr(token t1){
		if(getStr()==t1.getStr()) return true;
		else return false;
	}
	
	/**
	 * 获得对象的哈希值
	 * @return token的哈希值
	 * */
	@Override
	public int hashCode() {
		return this.str.hashCode();
	}
	
	/**
	 * 将表示数字的字符串转化成数字
	 * @param str 表示数字的字符串
	 * @return 代表数字的token
	 * */
	public static token StrToNum(String str){
		token tmp = new token(3, 0, str, '\0', 0, 0);		//3代表数字；index2以后再改变
		char tmpch=0;
		int flag1=0;
		double f2=10;
		for(int i=0;i<str.length();i++){
			tmpch=str.charAt(i);
			if(tmpch>='0' && tmpch<='9'){
				if(flag1==0){
					tmp.setNum2(tmp.getNum2() * 10 + (tmpch-'0'));
				}
				else{
					tmp.setNum1(tmp.getNum1() + (tmpch-'0')/f2);
					f2 *=10;
				}
			}
			else if(tmpch=='.')							//遇到小数点
			{
				tmp.setNum1(1.0 * tmp.getNum2());
				flag1=1;
			}
		}
		return tmp;
	}

}
