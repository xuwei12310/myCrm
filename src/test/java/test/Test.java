/**
 * 
 */
package test;

/**
 * @description:
 * @createTime 2018年3月10日 下午3:32:47
 * @copyright 福建骏华有限公司（c）2017
 * @author xw
 *
 */
public class Test {
	static String s;
	public static void main(String[] args) {
		s=doTest();
		System.out.println("最终结果是:"+s);
	}
	public static String doTest(){
		try{
			System.out.println("try");
			return s="a";
		}
		finally{
			System.out.println("enter finally s="+s);
			System.out.println("finally");
			s="b";
			System.out.println("S="+s);
		}
	}
}
