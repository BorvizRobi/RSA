import java.util.Vector;
import java.math.BigInteger;
import java.util.Random;

public class Rsa{

    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");

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

/*
  public static BigInteger bovitettEuklidesz2(BigInteger a,BigInteger b){

	if(a.equals(ZERO)) return b;
	if(b.equals(ZERO)) return a;
      
	Vector<BigInteger> r = new Vector<BigInteger>();
	Vector<BigInteger> q = new Vector<BigInteger>();
	Vector<BigInteger> x = new Vector<BigInteger>();
	Vector<BigInteger> y = new Vector<BigInteger>();

	r.add(a);
	r.add(b);

	q.add(MINUS_ONE);
	
	x.add(ONE);
	x.add(ZERO);
	y.add(ZERO);
	y.add(ONE);

	int i=1;

	while(true){
		
	   q.add(r.get(i-1).divide(r.get(i)));
	   r.add(r.get(i-1).mod(r.get(i))); if(r.lastElement().equals(ZERO)) break;
	   x.add(x.get(i).multiply(q.get(i)).add(x.get(i-1)));
	   y.add(y.get(i).multiply(q.get(i)).add(y.get(i-1)));

	   i=i+1;
	   }
   	

	BigInteger X=MINUS_ONE.pow(r.size()-2).multiply(x.get(r.size()-2));
	BigInteger Y=MINUS_ONE.pow(r.size()-1).multiply(y.get(r.size()-2));
	System.out.println(X);
	System.out.println(Y);
	return a.multiply(X).add(b.multiply(Y));
   }

*/

  public static BigInteger bovitettEuklidesz3(BigInteger a,BigInteger b){

	if(a.equals(ZERO)) return b;
	if(b.equals(ZERO)) return a;
      	if(b.compareTo(a) == 1) {BigInteger temp = a; a=b; b=temp;}

	BigInteger q;
	BigInteger lastx=ONE;
	BigInteger lasty=ZERO;
	BigInteger x=ZERO;
	BigInteger y=ONE;

	BigInteger temp1;
	BigInteger temp2;
	BigInteger temp3;

	while(!b.equals(ZERO)){
	
	   q=a.divide(b);
	   temp1= a.mod(b);
	   a=b;
	   b=temp1;

	   temp2=x;
	   x=lastx.subtract(q.multiply(x));
	   lastx=temp2;

	   temp3=y;
	   y=lasty.subtract(q.multiply(y));
	   lasty=temp3;

	}

	System.out.println(lastx);
	System.out.println(lasty);
	return a;
	

   }

   //a^b mod m megoldása
   public static BigInteger modularisGyorsHatvanyozas(BigInteger a, BigInteger b, BigInteger m){
	

   	String b_binarystring= b.toString(2);

	BigInteger answer = new BigInteger("1");
	BigInteger c=a.mod(m);

	for(int i = 0;i<b_binarystring.length();i++){	

		if(b_binarystring.charAt((b_binarystring.length()-1)-i)=='1') 
		   answer = answer.multiply(c);

		c=c.pow(2).mod(m);
			
	}

	return answer.mod(m);


   }

   public static BigInteger randomBigInteger(BigInteger bottom, BigInteger top) {

        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
   }

   //Ha igazat ad vissza akkor n vagy prím vagy összetett, ha hamisat akkor n összetett
   public static boolean MR_PrimTeszt(BigInteger n, int k){  
   
      if (n.equals(ZERO) || n.equals(ONE)) 
         return false;

      if(n.equals(TWO)) 
         return true;

      if(n.mod(TWO).equals(ZERO)) 
         return false;
   
      BigInteger d = n.subtract(ONE);
      BigInteger S = new BigInteger("0");

      while(d.mod(TWO).equals(ZERO)){

	   d=d.divide(TWO);
	   S=S.add(ONE);

      }

      BigInteger tanu;

      WitnessLoop: for(int i=0;i<k;i++){

	if(i==0)
	  tanu = TWO;
	else
          tanu= randomBigInteger(TWO,n.add(BigInteger.valueOf(-2)));
	
        BigInteger x = modularisGyorsHatvanyozas(tanu,d,n);
	  
	if( x.equals(ONE) || x.equals(n.subtract(ONE)) ) 
	   continue WitnessLoop;

         for(int r=1;r<S.intValue();r++){
     	
	      x=x.pow(2).mod(n);
                       
	      if(x.equals(n.subtract(ONE))) 
		continue WitnessLoop;	
         

         }   
	return false;
         
      }
 
      return true;

   }


   public static void main(String[] args){



      //BigInteger a = new BigInteger("298098031222221343141341111111111111112222222222222222222222222222222222222222222222222222222222222222221213314");
      //BigInteger b = new BigInteger("4790132444444444444444444449128498725278678678678678678524367834618646313272321221341344444444443413414314141341314134134141341413");
      //BigInteger m = new BigInteger("10080983141341434565");
      
      //BigInteger c= modularisGyorsHatvanyozas(a,b,m);

	//BigInteger n = new BigInteger("6700417");

	
      //boolean prime = MR_PrimTeszt(n,6);
	//System.out.println(prime);



	BigInteger a1 = new BigInteger("180");
	BigInteger b1 = new BigInteger("150");
	BigInteger c1 = bovitettEuklidesz3(a1,b1);
	System.out.println(c1);
      
   }
}
