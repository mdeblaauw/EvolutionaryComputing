import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player100 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
    static int populationSize = 100;
    static int parentsSize = 200;
	static int number_childs = 100;
	
	public player100()
	{
		rnd_ = new Random();
	}
	
	public static void main(String[] args) {
		System.out.println("Start");
		
		player100 awesome = new player100();
		double[][] pop = awesome.init_population(populationSize, 10);
		double[] fitness = {1,2,10,3,2,4,9,6,7,1};
		
		for (double[] x : pop)
		{
		   for (double y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
		System.out.println("middle");
		pop = awesome.select_parents(pop, fitness, parentsSize);
		//System.out.println(pop[0].length);
		//print population for testing
		for (double[] x : pop)
		{
		   for (double y : x)
		   {
		        System.out.print(y + " ");
		   }
		   System.out.println();
		}
		//System.out.println(pop[0].length);
		System.out.println("next");
		
		double[][] children;
    	children = awesome.crossOver(pop, number_childs);
		
    	for (double[] x : children)
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
	public double[][] init_population(int populationSize, int dimension) {
		//Create 2-dimensional array, which is populationSize X 10 and continuous input in [-5,5]
		//Do this randomly with 10 dimensional uniform distribution
		Random generate = new Random();
		double[][] pop = new double[populationSize][dimension];
		
		for(int x = 0; x < populationSize; x++) {
			for(int y = 0; y<dimension; y++) {
				pop[x][y] = (generate.nextDouble() * 10) - 5;
			}
		}
		return(pop);
	}
	
	//Select parents from population with roulette wheel style
	public double[][] select_parents(double[][] population, double[] pop_fitness, int parents) {
		double[][] parents_pop = new double[parents][10];
		double totalWeight  = 0.0d;
		
		//Calculate total fitness of population
		for(int i = 0; i < pop_fitness.length; i++) {
			totalWeight += pop_fitness[i];
		}
		
		//Select random individual with respect to fitness value
		for(int j = 0; j < parents; j++) {
			int randomIndex = -1;
			double random = Math.random() * totalWeight;
			for(int i = 0; i < pop_fitness.length; i++) {
				random -= pop_fitness[i];
				if(random<=0.0d) {
					randomIndex = i;
					break;
				}
			}
			parents_pop[j] = population[randomIndex];
		}
		return parents_pop;
	}
	
	//Do cross-over by taking average
	public double[][] crossOver(double[][] population, int number_childs) {
		double[][] childs = new double[number_childs][10];
		
		for(int i = 0; i<parentsSize; i+=2) {
			for(int j = 0; j<10; j++) {
				childs[i/2][j] = (population[i][j] + population[i+1][j])/2;
			}
		}
		return childs;
	}
	
	//Calculate fitness
	public double[] calculate_fitness(double[][] population, int population_number) {
		double pop_fitness[] = new double[population_number];
        for(int x = 0; x<population_number; x++) {
        	pop_fitness[x] = (double) evaluation_.evaluate(population[x]);
        }
        return pop_fitness;
	}
	
	public void run()
	{
		System.out.println(evaluations_limit_);
		// Run your algorithm here
        
        int evals = 0;
        // init population
        double[][] population = init_population(populationSize, 10);
        // calculate fitness
        double pop_fitness[];
        pop_fitness = calculate_fitness(population, populationSize);
        
        //double pop_fitness[] = new double[populationSize];
        //for(int x = 0; x<populationSize; x++) {
        //	pop_fitness[x] = (double) evaluation_.evaluate(population[x]);
        //}
        System.out.println("start");
        int i = 0;
        while(evals<evaluations_limit_){
        	System.out.println(i);
            // Select parents
        	population = select_parents(population, pop_fitness, parentsSize);
            // Apply crossover / mutation operators
        	double[][] children;
        	children = crossOver(population, number_childs);
            //double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            //Check fitness of unknown fuction
            //Double fitness = (double) evaluation_.evaluate(child);
        	double[] children_fitness;
        	children_fitness = calculate_fitness(children, number_childs);
            evals++;
            // Select survivors (Replace on basis of fitness)
            population = children;
            pop_fitness = children_fitness;
            i++;
        }

	}
}
