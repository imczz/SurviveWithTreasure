package czz.compilation;

import java.util.ArrayList;

/**
 * 有限状态自动机，实现抽象父类的getchar方法
 * @author CZZ
 * */
public class FiniteStateMachine extends AutomataMachine{
	
	/**
	 * 错误的状态（便于调试）
	 * */
	ArrayList<Integer> err_state;
	
	/**
	 * 字符缓冲区
	 * */
	StringBuffer buffer;

	/*================================方法 methods================================*/
	
	/**
	 * 构造方法
	 * @param mode 模式——0:未启用。1:ifelse。2:状态转换表
	 * @param type 自动机类型——KTiT、cT、sT、CT、PT
	 * */
	public FiniteStateMachine(int mode, String type){
		this.start_state = 0;										//起始状态0
		this.buffer = new StringBuffer();							//初始化缓冲区
		this.err_state = new ArrayList<Integer>();					//初始化错误状态集合
		if (type == "KTiT") {								//KT关键字;iT标识符
			this.name = type;
			end_state.add(2);
			this.err_state.add(3);
		}
		else if (type == "cT") {					//字符
			this.name = type;
			end_state.add(3);
			this.err_state.add(4);
			this.err_state.add(5);
		}
		else if (type == "sT") {					//字符串
			this.name = type;
			end_state.add(2);
			this.err_state.add(3);
		}
		else if (type == "CT") {					//常数
			this.name = type;
			end_state.add(3);
			end_state.add(4);
			this.err_state.add(5);
			this.err_state.add(6);
		}
		else if (type == "PT") {					//界符
			this.name = type;
			for(int i = 1; i <= 44; i++) {
				if (i != 1 && i != 5 && i != 9 && i != 12 && i != 15 && i != 18 && i != 21 && i != 24 && i != 28)
					end_state.add(i);
			}
			this.err_state.add(101);
		}
		else if (type == "others") {				//其他字符
			this.name = type;
			end_state.add(1);
			end_state.add(2);
			this.err_state.add(3);
		}
		if (mode == 1 || mode == 2) this.useable = mode;
		else mode = 0;
	}
	
	/**
	 * if else临时自动机的转换函数（不改变当前状态，试探性的接收）
	 * @param tmpstate 某一个状态
	 * @param ch 在tmpstate状态下待接收的字符
	 * @return 转换到的状态
	 * */
	public int state_change1(int tmpstate, char ch) {
		//int tmpstate = 0;
		if (this.name == "KTiT") {
			if (tmpstate == 0) {
				if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') tmpstate = 1;
				else tmpstate = 3;			//err
			}
			else if (tmpstate == 1) {
				if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z' || ch >= '0' && ch <= '9' || ch == '_') tmpstate = 1;
				else tmpstate = -2;
			}
			else {
				return 0;
			}
		}
		else if (this.name == "cT") {
			if (tmpstate == 0) {
				if (ch == '\'') tmpstate = 1;
				else tmpstate = 4;				//err
			}
			else if (tmpstate == 1) {
				tmpstate = 2;
				//else tmpstate = 2;
			}
			else if (tmpstate == 2) {
				if (ch == '\'') tmpstate = 3;
				else tmpstate = 5;			//err
			}
			else {
				return 0;
			}
		}
		else if (this.name=="sT"){
			if (tmpstate == 0)
			{
				if (ch == '\"') tmpstate = 1;
				else tmpstate = 3;				//err
			}
			else if (tmpstate == 1)
			{
				if (ch!='\"') tmpstate = 1;
				else tmpstate = 2;
			}
			else
			{
				return 0;
			}
		}
		else if (this.name=="CT"){
			if (tmpstate == 0)
			{
				if (ch >= '0' && ch <= '9') tmpstate = 1;
				//else if (ch == '.') tmpstate = 2;
				else tmpstate = 5;				//err
			}
			else if (tmpstate == 1)
			{
				if (ch >= '0' && ch <= '9') tmpstate = 1;
				else if (ch == '.') tmpstate = 2;
				else tmpstate = -3;
			}
			else if (tmpstate == 2)
			{
				if (ch >= '0' && ch <= '9') tmpstate = 2;
				else if (ch == '.') tmpstate = 6;			//err
				else tmpstate = -4;						//back one step
			}
			else
			{
				return 0;
			}
		}
		else if (this.name == "PT"){
			if (tmpstate == 0)
			{
				if (ch == '>') tmpstate = 1;
				else if (ch == '<') tmpstate = 5;
				else if (ch == '=') tmpstate = 9;
				else if (ch == '!') tmpstate = 12;
				else if (ch == '|') tmpstate = 15;
				else if (ch == '&') tmpstate = 18;
				else if (ch == '+') tmpstate = 21;
				else if (ch == '-') tmpstate = 24;
				else if (ch == ':') tmpstate = 28;
				else if (ch == '*') tmpstate = 31;
				else if (ch == '/') tmpstate = 32;
				else if (ch == '(') tmpstate = 33;
				else if (ch == ')') tmpstate = 34;
				else if (ch == '[') tmpstate = 35;
				else if (ch == ']') tmpstate = 36;
				else if (ch == '{') tmpstate = 37;
				else if (ch == '}') tmpstate = 38;
				else if (ch == ';') tmpstate = 39;
				else if (ch == ',') tmpstate = 40;
				else if (ch == '.') tmpstate = 41;
				else if (ch == '%') tmpstate = 42;
				else if (ch == '?') tmpstate = 43;
				else if (ch == '^') tmpstate = 44;
				else tmpstate = 101;				//err
			}
			else if (tmpstate == 1)
			{
				if (ch == '=') tmpstate = 2;
				else if (ch == '>')  tmpstate = 3;
				else tmpstate = -4;
			}
			else if (tmpstate == 5)
			{
				if (ch == '=') tmpstate = 6;
				else if (ch == '<')  tmpstate = 7;
				else tmpstate = -8;
			}
			else if (tmpstate == 9)
			{
				if (ch == '=') tmpstate = 10;
				else tmpstate = -11;
			}
			else if (tmpstate == 12)
			{
				if (ch == '=') tmpstate = 13;
				else tmpstate = -14;
			}
			else if (tmpstate == 15)
			{
				if (ch == '|') tmpstate = 16;
				else tmpstate = -17;
			}
			else if (tmpstate == 18)
			{
				if (ch == '&') tmpstate = 19;
				else tmpstate = -20;
			}
			else if (tmpstate == 21)
			{
				if (ch == '+') tmpstate = 22;
				else tmpstate = -23;
			}
			else if (tmpstate == 24)
			{
				if (ch == '-') tmpstate = 25;
				if (ch == '>') tmpstate = 26;
				else tmpstate = -27;
			}
			else if (tmpstate == 28)
			{
				if (ch == ':') tmpstate = 29;
				else tmpstate = -30;
			}
			else
			{
				return 0;
			}
		}
		if (this.name=="others"){
			if (tmpstate == 0)
			{
				if (ch == ' ' || ch == '\t' || ch == '\r' || ch ==  '\n' || ch == '\0') tmpstate = 1;
				else if (ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') tmpstate = -2;
				else if (ch >= '0' && ch <= '9') tmpstate = -2;
				else if (TokenList.strtable.contains(ch)) tmpstate = -2;
				else tmpstate = 3;			//err
			}
			else
			{
				return 0;
			}
		}
		return tmpstate;
	}
	
	/**
	 * 接收一个字符
	 * @param ch 待接收的字符
	 * @return >0当前状态，<0当前状态的负数（返还终结符给下一个自动机），0错误
	 * */
	public int getchar(char ch){
		int ret = 0;
		if (useable == 0){							//模式不正确
			System.out.println("automata machine must be prepared before use.");
		}
		else if (useable == 1){					//ifelse的自动机
			state = state_change1(state, ch);	//下一个状态

			if (!end_state.contains(Integer.valueOf(Math.abs(state)))){		//不是结束状态
				if (this.err_state.contains(Integer.valueOf(state))) ret = -2;	//错误的状态
				else {
					buffer.append(ch);									//缓冲区增加当前字符
					ret = 0;						//下一个
				}
			}
			else{						//结束状态
				if (state == 0){			
					System.out.println("0?");		//刚开始就结束了?
				}
				if (state > 0) buffer.append(ch);	//如果为正则接收，为负说明不属于当前token，需要“退回”缓冲区，由下一个自动机接收
				ret = state;						//当前状态
			}
		}
		return ret;
	}
}
