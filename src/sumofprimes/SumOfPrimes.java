/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sumofprimes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author huang
 */
public class SumOfPrimes {

    /**
     * @param args the command line arguments
     */
    static Scanner sc=new Scanner(System.in);
    static HashSet<Integer> primes=new HashSet();
    static HashMap<Integer,Integer> previousPrimes=new HashMap();
    public static void main(String[] args) {
        // TODO code application logic here
        for (int i=0;i<5;i++) {
            int number=sc.nextInt();
            int[] result=sumOfPrimes(number);
            printResult(number,result);
        }
    }

    private static int[] sumOfPrimes(int number) {
       int[] result;
       if (isPrime(number)) {
           result=new int[1];
           result[0]=number;
           return result;
       }
       result= sumOf2Primes(number);
       if (result!=null) return result;
       else return sumOf3Primes(number);
    }

    private static int[] sumOf2Primes(int number) {
        if (number%2==0) {
            return sumOf2OddPrimes(number,3,number);
        } 
        return sumOf3OddPrimes(number);
    }

    private static int[] sumOf3Primes(int number) {
        if (number%2==0) {
            return null;
        } else {
            int[] result=sumOf3OddPrimes(number);
            if (result!=null) return result;
            else if (isPrime(number-4)){
                result=new int[3];
                result[0]=2;
                result[1]=2;
                result[3]=number-4;
                return result;
            }
            return null;
        }
    }

    private static void printResult(int number,int[] result) {
        if (result==null) {
            System.out.println("No solution for "+number);
            return;
        }
        System.out.print(number+" = "+result[0]);
        for (int i=1;i<result.length;i++) {
            System.out.print(" + "+result[i]);
        }
        System.out.println();
    }

    private static int[] sumOf2OddPrimes(int number,int min,int max) {
        int p=number/2;
        if (p%2==0) p--;
        int q=number-p;
        boolean found=false;
        while (p>=min && q<=max) {
            if (isPrime(p) && isPrime(q)) {
                found=true;
                break;
            }
            p-=2;
            q+=2;
        } 
        if (found) {
            int[] result={p,number-p};
            return result;
        }
        return null;
    }

    private static boolean isPrime(int n) {
        if (n==2 || n==3) return true;
        if (primes.contains(n)) return true;
        if (n%2==0) return false;
        int i=3;
        while (i*i<=n) {
            if (n%i==0) return false;
            i+=2;
        }
        primes.add(n);
        return true;
    }

    private static int[] sumOf3OddPrimes(int number) {
        if (number<9) return null;
        int p=number/3;
        
        do  {
            p=getPreviousPrime(p);
            int[] partRes=sumOf2OddPrimes(number-p,p,number);
            if (partRes!=null) {
                int[] result=new int[3];
                result[0]=p;
                result[1]=partRes[0];
                result[2]=partRes[1];
                return result;
            }
            p--;
        } while (p>3);
        return null;
    }

    private static int getPreviousPrime(int num) {
        if (num==2) return num;
        if (previousPrimes.containsKey(num)) return previousPrimes.get(num);
        int n=num;
        if(n%2==0) n--;
        while (n>=3) {
            if (isPrime(n)) 
            {
                previousPrimes.put(num, n);
                return n;
            }
            n-=2;
        }
        previousPrimes.put(num, -1);
        return -1;
    }
    
}
