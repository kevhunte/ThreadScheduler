import java.util.Random;
import java.util.Scanner;

public class Main {
	
	static void executionTime(String alg,String[] threadList){
		int sum = 0;
		int PJ = 25;//pre job
		int RJ = 0;//remaining job
		int[] MaxPJ_RJ = {5,0};//max pre job and paired remaining job
		int[] MaxRJ_PJ = {5,0};//max remaining job and paired pre job
		
		if(alg.contains("FCFS")){
			for(String thread: threadList){//iterates through threads
				String[] threadNums = thread.split(" ");//holds the split numbers
				if(PJ > Integer.parseInt(threadNums[0]))
					PJ = Integer.parseInt(threadNums[0]);//will get min of pre jobs
				sum+= Integer.parseInt(threadNums[1]);//adds all of the locked jobs
				/*Will store the largest pre job and its pairing post job */
				if(MaxPJ_RJ[0] < Integer.parseInt(threadNums[0])){
					MaxPJ_RJ[0] = Integer.parseInt(threadNums[0]);
					MaxPJ_RJ[1] = Integer.parseInt(threadNums[2]);
				}
			}
			sum+=PJ;//adds the smallest Pre job val
			sum+=MaxPJ_RJ[1];//adds the post job paired with the largest pre job
			System.out.println("The execution time for this " + alg+ " is:"+sum);
		}else if(alg.contains("LRJF")){
			for(String thread: threadList){
				String[] threadNums = thread.split(" ");//holds the split numbers
				if(RJ < Integer.parseInt(threadNums[2]))
					RJ = Integer.parseInt(threadNums[2]);//will get max of remaining jobs
				sum+= Integer.parseInt(threadNums[1]);//sums up lock jobs
				/*Will store the largest remaining job and its pairing pre job */
				if(MaxRJ_PJ[0] < Integer.parseInt(threadNums[2])){
					MaxRJ_PJ[0] = Integer.parseInt(threadNums[2]);
					MaxRJ_PJ[1] = Integer.parseInt(threadNums[0]);
				}
			}
			sum+=RJ;//adds largest remaining job to sum
			sum+=MaxRJ_PJ[1];//adds the pre job val that pairs with LRJ
			System.out.println("The execution time for this " + alg+ " algorithm is: "+sum);
		}else
			System.out.println("Execution time for Branch and Bound not necessary");
		
		
	}
	
	static String schedulePicker(String[] combo){
		System.out.println("For this thread combination, the best algorithm to use is:");
		if(combo[0].contains("=")){
			System.out.println("LRJF");
			return "LRJF";
		}
		else if(combo[0].contains("<>") && combo[2].contains("=")){
			System.out.println("FCFS");
			return "FCFS";
		}
		else{
			System.out.println("Complex algorithm, Branch and Bound");
			return "Other";
		}
	}
	
	static void threadGenerator(String[] combo, int number, String[] list){
		Random random = new Random();
		String PJ = (random.nextInt(20)+5) + " ";//makes numbers between 5 and 25
		String LJ = (random.nextInt(45)+5) + " ";
		String LRJ = (random.nextInt(20)+5) + " ";
		System.out.println("\nGenerating threads...");
		
		for(int i = 0; i < number; i++){
			if(combo[0].contains("<>"))
				PJ = (random.nextInt(20)+5) + " ";//generate when different
			if(combo[1].contains("<>"))
				LJ = (random.nextInt(45)+5) + " ";//generate when different
			if(combo[2].contains("<>"))
				LRJ = (random.nextInt(20)+5) + " ";//generate when different
			
			list[i] = PJ+LJ+LRJ;//Pre, Lock and Remaining values
		}
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] threadCombo = new String[3];
		int threadNum = 0;
		
		String algorithm = "";
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter number of threads:");
		threadNum = input.nextInt();//stores number of threads
		
		String[] threadList = new String[threadNum];//will store threads
		
		System.out.println("Enter prelock job for threads: (= or <>))");
		threadCombo[0] = input.next();//store PLJ in first spot
		
		System.out.println("Enter lock job for threads: (= or <>))");
		threadCombo[1] = input.next();//store LJ in second spot
		
		System.out.println("Enter post-lock job: (= or <>))");
		threadCombo[2] = input.next();//store RJ in last spot

		algorithm = schedulePicker(threadCombo);//will pick the best algorithm
		
		threadGenerator(threadCombo, threadNum, threadList);//generates array of threads
		
		for(String a:threadList)
			System.out.println(a);//debugging to make sure threads were made correctly
		
		executionTime(algorithm,threadList);
	}

}
