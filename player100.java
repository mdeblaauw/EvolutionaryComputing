import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player100 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
    static int populationSize = 10;
	
	public player100()
	{
		rnd_ = new Random();
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		
		//print population for testing
		double[][] pop = init_population(populationSize, 10);
		
		for (double[] x : pop)
		{
		   for (double y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
	}
	
	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;
		
		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));
        System.out.println(isMultimodal);
        System.out.println(hasStructure);
        System.out.println(isSeparable);
		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }
    
	//Create initial population
	public static double[][] init_population(int populationSize, int dimension) {
		//Create 2-dimensional array, which is populationSize X 10 and continuous input in [-5,5]
		//Do this randomnly with 10 dimensional uniform distribution
		Random generate = new Random();
		double[][] pop = new double[populationSize][dimension];
		
		for(int x = 0; x < populationSize; x++) {
			for(int y = 0; y<dimension; y++) {
				pop[x][y] = (generate.nextDouble() * 10) - 5;
			}
		}
		return(pop);
	}
	
	//Select parents from population
	public void select_parents() {
		
	}
	
	public void run()
	{
		System.out.println(evaluations_limit_);
		// Run your algorithm here
        
        int evals = 0;
        // init population
        double[][] population = init_population(populationSize, 10);
        // calculate fitness
        while(evals<evaluations_limit_){
            // Select parents
        	
            // Apply crossover / mutation operators
            double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            evals++;
            // Select survivors
        }

	}
}
