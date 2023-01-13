/*
 * MIT License
 * 
 * Copyright (c) 2023 Muhammad Mahran Mazlan
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package scheduler;

import java.util.Scanner;
import java.util.Random;

/**
 * Last updated on: 10:23, 13/01/2023
 * @author MMBM
 */
public class Scheduler
{
    int n;
    String[] processes;
    double[] arrivals, bursts, ranks;
    boolean[] dones;
    
    /**
     * Constructor
     * @param n - number of process
     * @param processes - String array with names of process
     * @param arrivals - double array to store arrivals of process
     * @param bursts - double array to store bursts of process
     */
    public Scheduler(int n, String[] processes, double[] arrivals, double[] bursts)
    {
        this.n = n;
        this.processes = processes;
        this.arrivals = arrivals;
        this.bursts = bursts;
    }
    
    /**
     * Method to initialize input as a random data from about 3 to 10 processes
     * @return Scheduler for method chain
     */
    static Scheduler randomData()
    {
        Random r = new Random();
        
        int n = 3 + r.nextInt(8);
        
        String[] processes = new String[n];
        double[] arrivals = new double[n];
        double[] bursts = new double[n];
        
        for (int i = 0; i < n; i++)
        {
            processes[i] = new StringBuilder("Process ").append(i + 1).toString();

            arrivals[i] = r.nextInt(100);

            bursts[i] = r.nextInt(100);
        }

        return new Scheduler(n, processes, arrivals, bursts);
    }
    
    /**
     * Method to initialize input as the data from the Round Robin practice in MS Excel
     * @return Scheduler for method chain
     */
    static Scheduler roundRobinData()
    {
        int n = 7;

        String[] processes = new String[n];
        double[] arrivals = new double[]
        {
            0, 0, 0, 0, 58, 90, 100
        };
        double[] bursts = new double[]
        {
            45, 8, 38, 34, 46, 32, 38
        };

        for (int i = 0; i < n; i++)
        {
            processes[i] = new StringBuilder("Process ").append(i + 1).toString();
        }

        return new Scheduler(n, processes, arrivals, bursts);
    }

    /**
     * Method to initialize input as the data from the week 13 lab
     * @return Scheduler for method chain
     */
    static Scheduler week13Data()
    {
        int n = 5;

        String[] processes = new String[n];
        double[] arrivals = new double[]
        {
            0, 0, 5, 30, 122
        };
        double[] bursts = new double[]
        {
            12, 1, 16, 20, 22
        };

        for (int i = 0; i < n; i++)
        {
            processes[i] = new StringBuilder("Process ").append(i + 1).toString();
        }

        return new Scheduler(n, processes, arrivals, bursts);
    }
    
    /**
     * Method to initialize input as the data from the test
     * @return Scheduler for method chain
     */
    static Scheduler testData()
    {        
        int n = 5;

        String[] processes = new String[n];
        double[] arrivals = new double[]
        {
            0, 0, 4, 15, 21
        };
        double[] bursts = new double[]
        {
            4.8, 3.6, 3.1, 2.4, 4.5
        };

        for (int i = 0; i < n; i++)
        {
            processes[i] = new StringBuilder("Process ").append(i + 1).toString();
        }

        return new Scheduler(n, processes, arrivals, bursts);
    }
    
    /**
     * Method to get input for all the process arrivals and burst
     * @return Scheduler for method chain
     */
    static Scheduler getInput()
    {
        Scanner s = new Scanner(System.in);
        
        System.out.println("Enter amount of data: ");
        int n = s.nextInt();
        
        System.out.println("");

        String[] processes = new String[n];
        double[] arrivals = new double[n];
        double[] bursts = new double[n];

        for (int i = 0; i < n; i++)
        {
            s.nextLine();
            processes[i] = "Process " + (i + 1);
            System.out.println(processes[i]);

            System.out.print("Enter arrival : ");
            arrivals[i] = s.nextDouble();

            System.out.print("Enter burst   : ");
            bursts[i] = s.nextDouble();

            System.out.println("");
        }
        
        return new Scheduler(n, processes, arrivals, bursts);
    }
    
    /**
     * Algorithm to sort all processes based on arrival in case of defect data
     */
    void sortArrival()
    {
        double tmp;
        String tmpString;
        for (int i = 0; i < n; i++)
        {

            for (int j = 1; j < n - i; j++)
            {

                if (arrivals[j - 1] > arrivals[j])
                {
                    tmp = arrivals[j - 1];
                    arrivals[j - 1] = arrivals[j];
                    arrivals[j] = tmp;

                    tmp = bursts[j - 1];
                    bursts[j - 1] = bursts[j];
                    bursts[j] = tmp;
                    
                    tmpString = processes[j -1];
                    processes[j - 1] = processes[j];
                    processes[j] = tmpString;
                }
            }
        }
    }
    
    /**
     * First Come First Serve algorithm
     */
    void firstComeFirstServe()
    {
        sortArrival();
        
        double cur = 0d;
        double sum = 0d;

        final int SPAN = 12;
        System.out.printf
        (
                ("%-" + SPAN + "s").repeat(6) + "\n",
                "Process", "Arrival", "Start", "Burst", "End", "Waiting Time"
        );

        for (int i = 0; i < n; i++)
        {
            double start = Math.max(cur, arrivals[i]);
            double end = start + bursts[i];
            double waitingTime = start - arrivals[i];

            System.out.printf
            (
                    ("%-" + SPAN + "s") + ("%-" + SPAN + ".2f").repeat(5) + "\n",
                    processes[i], arrivals[i], start, bursts[i], end, waitingTime
            );

            sum += waitingTime;
            cur = end;
        }
        System.out.println("\nAverage waiting Time = " + sum / n);
    }
    
    /**
     * Short Job First algorithm
     */
    public void shortJobFirst()
    {
        sortArrival();
        
        double cur = 0d;
        double sum = 0d;

        final int SPAN = 13;
        System.out.printf
        (
                ("%-" + SPAN + "s").repeat(6) + "\n",
                "Process", "Arrival", "Start", "Burst", "End", "Waiting Time"
        );
        
        for (int i = 0; i < n; i++)
        {
            cur = Math.max(cur, arrivals[i]);
            
            double minBurst = 100000d;
            int indexBurst = -1;
            
            for (int j = 0; j < n; j++)
            {
                if (bursts[j] != 0 && arrivals[j] <= cur && bursts[j] < minBurst)
                {
                    minBurst = bursts[j];
                    indexBurst = j;
                }
            }
            
            int x = indexBurst;
            double start = cur;
            double end = start + bursts[x];
            double waitingTime = start - arrivals[x];

            System.out.printf
            (
                    ("%-" + SPAN + "s") + ("%-" + SPAN + ".2f").repeat(5) + "\n",
                    processes[x], arrivals[x], start, bursts[x], end, waitingTime
            );

            bursts[x] = 0;
            sum += waitingTime;
            cur = end;
        }
        System.out.println("");
        
        System.out.printf("Average Waiting Time: %.2f\n", sum / n);
    }
    
    /**
     * Ranked Short Job First taken from Short Job First and introduce aging to decide which process to execute first
     */
    public void rankedShortJobFirst()
    {
        sortArrival();
        
        final double WAITING_WEIGHTAGE = 1;
        final double BURST_WEIGHTAGE = 2;

        double cur = 0d;
        double sum = 0d;

        final int SPAN = 13;
        System.out.printf(
                ("%-" + SPAN + "s").repeat(6) + "\n",
                "Process", "Arrival", "Start", "Burst", "End", "Waiting Time"
        );

        for (int i = 0; i < n; i++)
        {
            cur = Math.max(cur, arrivals[i]);

            double minRank = 0;
            int indexBurst = -1;

            for (int j = 0; j < n; j++)
            {
                if (bursts[j] != 0 && arrivals[j] <= cur)
                {
                    ranks[j] = ((cur - arrivals[j]) * WAITING_WEIGHTAGE) + (bursts[j] * BURST_WEIGHTAGE);
                }
            }
            
            for (int j = 0; j < n; j++)
            {
                if (bursts[j] != 0 && arrivals[j] <= cur && ranks[j] <= minRank)
                {
                    minRank = ranks[j];
                    indexBurst = j;
                }
            }

            int x = indexBurst;
            double start = cur;
            double end = start + bursts[x];
            double waitingTime = start - arrivals[x];

            System.out.printf(
                    ("%-" + SPAN + "s") + ("%-" + SPAN + ".2f").repeat(5) + "\n",
                    processes[x], arrivals[x], start, bursts[x], end, waitingTime
            );

            bursts[x] = 0;
            sum += waitingTime;
            cur = end;
        }
        System.out.println("");

        System.out.printf("Average Waiting Time: %.2f\n", sum / n);
    }
    
    /**
     * Original Round Robin
     */
    public void roundRobin()
    {
        Scanner s = new Scanner(System.in);
        
        sortArrival();

        double cur = 0d;
        double sum = 0d;
        
        System.out.print("Enter quantum number: ");
        double QUANTUM = s.nextDouble();
        System.out.println();

        final int SPAN = 13;
        System.out.printf(
                (" %-" + SPAN + "s|").repeat(7) + (" %-" + SPAN + "s") + "\n",
                "Process", "Arrival", "Burst", "Start", "Process Time", "Complete", "Waiting Time", "Remaining"
        );
        
        boolean condition = true;
        
        while (condition)
        {
            sortArrival();
            
            //Determine which arrive first
            for (int i = 0; i < n; i++)
            {
                if (bursts[i] > 0)
                {
                    cur = Math.max(cur, arrivals[i]);
                    break;
                }
            }
            
            int count = 0;
            
            for (int i = 0; i < n; i++)
            {
                if (arrivals[i] > cur)
                {
                    break;
                }
                
                if (bursts[i] > 0 && arrivals[i] <= cur)
                {
                    count++;
                }
            }
            
            System.out.println(("-".repeat(SPAN + 1) + "+").repeat(7) + "-".repeat(SPAN + 1));
            
            for (int i = 0; count > 0; i++)
            {
                if (bursts[i] > 0 && arrivals[i] <= cur)
                {
                    double pTime = Math.min(bursts[i], QUANTUM);
                    
                    double start = cur;
                    double end = start + pTime;
                    double waitingTime = start - arrivals[i];
                    double remaining = bursts[i] - pTime; // remaining


                    System.out.printf(
                            (" %-" + SPAN + "s|") + (" %-" + SPAN + ".2f|").repeat(6) + (" %-" + SPAN + ".2f") + "\n",
                            processes[i], arrivals[i], bursts[i], start, pTime, end, waitingTime, remaining
                    );

                    bursts[i] = remaining;
                    arrivals[i] = end;
                    sum += waitingTime;
                    cur = end;
                    count--;
                }
            }
            
            condition = false;
            
            for (double burst: bursts)
            {
                if (burst > 0)
                {
                    condition = true;
                    break;
                }
            }
        }
        System.out.println("");

        System.out.printf("Average Waiting Time: %.2f\n", sum / n);
    }
    
    /**
     * Round Robin algorithm v3 taken idea from Nasa combined with own idea to further optimize the algorithm
     */
    void roundRobinNasaImprovise()
    {
        //completed to indicate how many process has already been completed
        int completed = 0;

        //Get the amount of quantum
        System.out.print("Enter quantum number: ");
        double quantum = new Scanner(System.in).nextDouble();
        System.out.println();

        //For printing purpose only
        final int SPAN = 13;

        //Print Header
        System.out.printf(
                (" %-" + SPAN + "s|").repeat(7) + (" %-" + SPAN + "s") + "\n",
                "Process", "Arrival", "Burst", "Start", "Process Time", "Complete", "Waiting Time", "Remaining"
        );

        //curr refer to current time, cycleUpdateCurr refer to time in cycle where it only updates after finishing a cycle
        double curr = 0d, sum = 0d;

        //Cycle loop
        while (completed != n)
        {
            //Print a line to differentiate cycle
            System.out.println(("-".repeat(SPAN + 1) + "+").repeat(7) + "-".repeat(SPAN + 1));

            //Sort all process based on their arrival
            sortArrival();

            //Approach to skip the gap between current time and arrival.
            //Loop to check which process arrive first.
            for (int i = 0; i < n; i++)
            {
                if (bursts[i] > 0)
                {
                    curr = Math.max(curr, arrivals[i]);
                    break;
                }
            }

            //cycleCurr is time for process loop and only updated in cycle loop.
            //Only use for condition to check whether the process is in the system or not.
            //Cannot use curr since curr is always updated in process loop, so the process that should not arrive in a cycle may appear if used.
            double cycleCurr = curr;

            //Process loop
            for (int i = 0; i < n; i++)
            {
                //Break loop to prevent unnecassary excess looping since all after this will not arrive yet
                if (arrivals[i] > cycleCurr)
                {
                    break;
                }

                //Check if the process is in the system and has not finished yet
                if (arrivals[i] <= cycleCurr && bursts[i] > 0)
                {
                    //Process time equal to quantum if burst is more than quantum otherwise equal to burst
                    double processTime;

                    if (bursts[i] > quantum)
                    {
                        processTime = quantum;
                    } else
                    {
                        processTime = bursts[i];

                        //Update completed since if process time equal to burst, the process will be completed in this loop
                        completed++;
                    }

                    //Calculation for all output
                    double start = curr;
                    double end = start + processTime;
                    double waitingTime = start - arrivals[i];
                    double remaining = bursts[i] - processTime;

                    //Print process, arrival, burst, start, processTime, end wiantingTime, remaining
                    System.out.printf(
                            (" %-" + SPAN + "s|") + (" %-" + SPAN + ".2f|").repeat(6) + (" %-" + SPAN + ".2f") + "\n",
                            processes[i], arrivals[i], bursts[i], start, processTime, end, waitingTime, remaining
                    );

                    // Update burst, arrival and current time
                    bursts[i] = remaining;
                    arrivals[i] = end;
                    curr = end;

                    //Update sum
                    sum += waitingTime;
                }

            }
        }
        System.out.println("");

        //Print average waiting time
        System.out.printf("Average Waiting Time: %.2f\n", sum / n);
    }

    /**
     * Round Robin algorithm v2 taken idea from Nasa and edited to correct the algorithm
     */
    void roundRobinNasa()
    {
        int completed = 0;

        System.out.print("Enter quantum number: ");
        double quantum = new Scanner(System.in).nextDouble();
        System.out.println();

        final int SPAN = 13;

        //Print Header
        System.out.printf(
                (" %-" + SPAN + "s|").repeat(7) + (" %-" + SPAN + "s") + "\n",
                "Process", "Arrival", "Burst", "Start", "Process Time", "Complete", "Waiting Time", "Remaining"
        );

        double curr = 0d, cycleUpdateCurr = 0d, sum = 0d;

        for (int i = 0; completed != n; i++)
        {
            if (i == n)
            {
                i = 0;
                cycleUpdateCurr = curr;
            }

            if (i == 0)
            {
                sortArrival();

                //Print line to differentiate cycle
                System.out.println(("-".repeat(SPAN + 1) + "+").repeat(7) + "-".repeat(SPAN + 1));
            }

            /*
            @TODO
            Below condition suppose to fix when there's a gap between current time and arrival
            but it's not fixed due to it checks all process burst not just the earliest one that haven't done yet
             */
//            if(bursts[i] > 0)
//            {
//                curr = Math.max(curr, arrivals[i]);
//            }
            if (arrivals[i] <= cycleUpdateCurr && bursts[i] > 0)
            {
                double processTime;

                if (bursts[i] > quantum)
                {
                    processTime = quantum;
                } else
                {
                    processTime = bursts[i];
                    completed++;
                }

                double start = curr;
                double end = start + processTime;
                double waitingTime = start - arrivals[i];
                double remaining = bursts[i] - processTime;

                //print process, arrival, burst, start, processTime, end wiantingTime, remaining
                System.out.printf(
                        (" %-" + SPAN + "s|") + (" %-" + SPAN + ".2f|").repeat(6) + (" %-" + SPAN + ".2f") + "\n",
                        processes[i], arrivals[i], bursts[i], start, processTime, end, waitingTime, remaining
                );

                bursts[i] = remaining;
                arrivals[i] = end;
                curr = end;
                sum += waitingTime;
            }
        }

        System.out.println("");

        System.out.printf("Average Waiting Time: %.2f\n", sum / n);
    }
}
