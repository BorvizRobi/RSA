import java.util.Vector;
import java.math.BigInteger;

public class Rsa{

/*
  public static int Euklidesz(int a,int b){
      
	Vector<Integer> r = new Vector<Integer>();
	Vector<Integer> q = new Vector<Integer>();

	r.add(a);
	r.add(b);

	q.add(-1);

	int i=1;

	while(r.lastElement()!= 0){
		
	   r.add(r.get(i-1)%r.get(i));
	   q.add(r.get(i-1)/r.get(i));
		
	   i=i+1;
	   }

	return r.get(r.size()-2);
   }
*/

  public static int bovitettEuklidesz(int a,int b){

	if(a==0) return b;
	if(b==0) return a;
      
	Vector<Integer> r = new Vector<Integer>();
	Vector<Integer> q = new Vector<Integer>();
	Vector<Integer> x = new Vector<Integer>();
	Vector<Integer> y = new Vector<Integer>();

	r.add(a);
	r.add(b);

	q.add(-1);
	
	x.add(1);
	x.add(0);
	y.add(0);
	y.add(1);

	int i=1;

	while(true){
		
	   q.add(r.get(i-1)/r.get(i));
	   r.add(r.get(i-1)%r.get(i)); if(r.lastElement()== 0) break;
	   x.add(x.get(i)*q.get(i)+x.get(i-1));
	   y.add(y.get(i)*q.get(i)+y.get(i-1));

	   i=i+1;
	   }
   	

	int X=(int)Math.pow(-1, r.size()-2)*x.get(r.size()-2);
	int Y=(int)Math.pow(-1, r.size()-1)*y.get(r.size()-2);
	System.out.println(X);
	System.out.println(Y);
	return a*X+b*Y;
   }

   public static BigInteger modularisGyorsHatvanyozas(BigInteger a, BigInteger b,BigInteger m){


   	String s= b.toString(2);
   	System.out.println(s);


	BigInteger answer = new BigInteger("1");
	BigInteger c=a.mod(m);

	for(int i = 0;i<s.length();i++){	

		if(s.charAt((s.length()-1)-i)=='1') 
		   answer = answer.multiply(c);

		c=c.pow(2).mod(m);
			
	}

	return answer.mod(m);


   }


   public static void main(String[] args){

      BigInteger a = new BigInteger("298098031222221343141341111111111111112222222222222222222222222222222222222222222222222222222222222222221213314");
      BigInteger b = new BigInteger("4790132444444444444444444449128498725278678678678678678524367834618646313272321221341344444444443413414314141341314134134141341413");
      BigInteger m = new BigInteger("10080983141341434565");
      //int c=bovitettEuklidesz(a,b);
      BigInteger c= modularisGyorsHatvanyozas(a,b,m);

      System.out.println(c);
   }
}
