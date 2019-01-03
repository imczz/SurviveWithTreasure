package czz.compilation;

import java.util.ArrayList;

/**
 * （抽象）自动机
 * @author CZZ
 * */
public abstract class AutomataMachine {
	
	/**
	 * 自动机的名字
	 * */
	String name;					//名字
	
	/**
	 * 自动机的起始状态序号（默认为0）
	 * */
	int start_state;
	
	/**
	 * 自动机的状态集合
	 * */
	ArrayList<Integer> Q;
	
	/**
	 * 结束（接收）状态集合
	 * */
	ArrayList<Integer> end_state;
	
	/**
	 * 字母表
	 * */
	ArrayList<Character> sigma;
	
	/**
	 * （未使用）状态转换表
	 * */
	ArrayList<ArrayList<Integer>> delta;		//状态转换表
	
	/**
	 * 自动机模式 0 不可用；1 转换表模式；2 if_else模式
	 * */
	int useable;
	
	/**
	 * 当前状态
	 * */
	protected int state;
	
	/*================================方法 methods================================*/
	
	/**
	 * 返回自动机当前状态
	 * @return 当前状态
	 * */
	public int retstate(){
		return state;
	}
	
	/**
	 * 构造方法
	 * */
	public AutomataMachine(){
		useable = 0;
		state = 0;
		Q = new ArrayList<Integer>();
		end_state = new ArrayList<Integer>();
		sigma = new ArrayList<Character>();
	}
	
	/**
	 * （抽象）接收一个字符
	 * @param ch 待接收的字符
	 * @return >0当前状态，<0当前状态的负数（返还终结符给下一个自动机），0错误
	 * */
	abstract public int getchar(char ch);
	
}
