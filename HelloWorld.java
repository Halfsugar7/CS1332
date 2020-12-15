public class HelloWorld {
	public static void main(String[] args) {
		int[] alpha ={1,2,3,4,5};
		int[] beta = new int[5];
		beta = alpha;
		System.out.println(beta[1]);
	}
}