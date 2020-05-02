import java.util.Vector;
import java.math.BigInteger;
import java.util.Random;

class euklideszEredmeny{ 

    private BigInteger lnko;
    private BigInteger x;
    private BigInteger y;

    public euklideszEredmeny(BigInteger lnko,BigInteger x,BigInteger y){

       this.lnko=lnko;
       this.x=x;
       this.y=y;

    }

    public BigInteger getLnko(){
       return lnko;
    }

    public BigInteger getX(){
       return x;
    }

    public BigInteger getY(){
       return y;
    }

} 


public class Rsa{

    private static final BigInteger MINUS_ONE = new BigInteger("-1");
    private static final BigInteger ZERO = BigInteger.ZERO;
    private static final BigInteger ONE = BigInteger.ONE;
    private static final BigInteger TWO = new BigInteger("2");
    private static final BigInteger THREE = new BigInteger("3");


    public static euklideszEredmeny bovitettEuklidesz(BigInteger a,BigInteger b){

	if(a.equals(ZERO)) return new euklideszEredmeny(b,ZERO,ONE);
	if(b.equals(ZERO)) return new euklideszEredmeny(a,ONE,ZERO);

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

	euklideszEredmeny eredmeny = new euklideszEredmeny(a,lastx,lasty);

	return eredmeny;
	
   }

   //a^b mod m megoldása
    public static BigInteger modularisGyorsHatvanyozas(BigInteger a, BigInteger b, BigInteger m){
	

   	String b_binarystring = b.toString(2);

	BigInteger answer = new BigInteger("1");
	BigInteger c = a.mod(m);

	for(int i = 0;i<b_binarystring.length();i++){	

		if(b_binarystring.charAt((b_binarystring.length()-1)-i) =='1') 
		   answer = answer.multiply(c);

		c = c.pow(2).mod(m);
			
	}

	return answer.mod(m);


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

	if(i == 0)
	  tanu = TWO;

	else{

          tanu = randomBigInteger(TWO,n.add(BigInteger.valueOf(-2)));

	  if(!tanu.mod(TWO).equals(ZERO)) 
	     tanu = tanu.add(ONE);
	}
	
        BigInteger x = modularisGyorsHatvanyozas(tanu,d,n);
	  
	if( x.equals(ONE) || x.equals(n.subtract(ONE)) ) 
	   continue WitnessLoop;

         for(int r=1;r<S.intValue();r++){
     	
	      x = x.pow(2).mod(n);
                       
	      if(x.equals(n.subtract(ONE))) 
		continue WitnessLoop;	
         

         }   

	return false;
         
      }
 
      return true;

    }

    public static BigInteger randomBigInteger(BigInteger bottom, BigInteger top) {

        Random rnd = new Random();
        BigInteger res;
        do {
            res = new BigInteger(top.bitLength(), rnd);
        } while (res.compareTo(bottom) < 0 || res.compareTo(top) > 0);
        return res;
    }

    public static BigInteger randomPrim(BigInteger min, BigInteger max){

	while(true){

	BigInteger p = randomBigInteger(min,max);
	if(!p.testBit(0)) p = p.subtract(ONE);
	
	if(MR_PrimTeszt(p,4)) return p;

	}
    }

    public static BigInteger find_e(BigInteger fi_n){

	BigInteger e = THREE;

	while(true){
	
	BigInteger lnko = bovitettEuklidesz(fi_n,e).getLnko();
	if(lnko.equals(ONE)) return e;
	
	e=e.add(TWO);

	}
    }

    public static void main(String[] args){

      //BigInteger a = new BigInteger("5");
     // BigInteger b = new BigInteger("117");
      //BigInteger m = new BigInteger("19");
      
      //BigInteger c= modularisGyorsHatvanyozas(a,b,m);
      //System.out.println(c);

	//BigInteger n = new BigInteger("97");

	
      //boolean prime = MR_PrimTeszt(n,6);
	//System.out.println(prime);



	//BigInteger a1 = new BigInteger("180");
	//BigInteger b1 = new BigInteger("150");
	//BigInteger c1 = bovitettEuklidesz3(a1,b1);
	//System.out.println(c1);
	
	
	BigInteger min = new BigInteger("10000000000000000000000000000000");
	BigInteger max = new BigInteger("10000000000000000000000000000000000000000000000000000000000000000000000000");

	BigInteger p= randomPrim(min,max);
	BigInteger q= randomPrim(min,max);


	System.out.println("p: " + p);
	System.out.println("q: " + q);

	BigInteger n = p.multiply(q);
	System.out.println("n: "+ n);

	BigInteger fi_n = p.subtract(ONE).multiply(q.subtract(ONE));
	System.out.println("fi_n: "+ fi_n);

	BigInteger e = find_e(fi_n);
	System.out.println("e:"+ e);

	euklideszEredmeny eredmeny = bovitettEuklidesz(e,fi_n);

	BigInteger d = eredmeny.getX();
	if(d.compareTo(ONE) == -1) d=d.add(fi_n);
	System.out.println("d: "+d);



	BigInteger uzenet = new BigInteger("12102301310323124124141564444444444444444444446464");
	System.out.println("Eredeti üzenet: " + uzenet);
	BigInteger titkositot_uzenet = modularisGyorsHatvanyozas(uzenet,e,n);
	System.out.println("Titkosított üzenet: " + titkositot_uzenet);

	BigInteger uzenet_visszafejtett=modularisGyorsHatvanyozas(titkositot_uzenet,d,n);
	System.out.println("Visszafejtett üzenet: " + uzenet_visszafejtett);

      
    }
}
