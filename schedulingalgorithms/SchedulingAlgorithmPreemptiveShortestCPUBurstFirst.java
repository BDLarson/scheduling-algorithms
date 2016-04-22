package schedulingalgorithms;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;
import java.util.*;

/*
* Blake Larson
*
* Preemptive Shortest CPU Burst First:
*	Allows processes to run for for a certain time slice (in this case 10),
*	when they are finished, the program will check a ready queue, from which
*	it pulls from. Any process that are ready but another is running, will fall
*	into this ready queue and wait for its turn.
*/

public class SchedulingAlgorithmPreemptiveShortestCPUBurstFirst implements SchedulingAlgorithm {
	// Create the priority queue, since shortest CPU burst runs on priority
	public static PriorityQueue<SimulatedProcess> priorityQ;

	public SchedulingAlgorithmPreemptiveShortestCPUBurstFirst() {
		// Create a new comparator with our overriden compare method, to compare 
		// multiple processes in the priority queue
		Comparator<SimulatedProcess> comparator = new ProcessComparator();
		priorityQ = new PriorityQueue<SimulatedProcess>(10, comparator);
	}

	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		// Check if the ready queue is empty first
		if (!priorityQ.isEmpty() && SchedulingMechanisms.getRunningProcess() == null) {
			// Run the next process on the top of the priority queue
			SchedulingMechanisms.dispatchProcess(priorityQ.poll(), -1);
		}
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// No time slice needed, since we are running processes fully based on priority
		// and not time to complete each
		return;
	}

	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// If the priority queue is empty and there are no running process, immediately
		// run the process
		if (SchedulingMechanisms.getRunningProcess() == null && priorityQ.isEmpty()) {
			SchedulingMechanisms.dispatchProcess(process, -1);
		}
		// If there is a process running then add new process to the priority queue.
		else if (SchedulingMechanisms.getRunningProcess() != null) {
			priorityQ.offer(process);
			// Check whether the current running process has a longer burst than the new process,
			// if so preempt it and run the shorter process
			if (SchedulingMechanisms.getProcessCPUBurstDuration(SchedulingMechanisms.getRunningProcess()) > 			    SchedulingMechanisms.getProcessCPUBurstDuration(priorityQ.peek())) {
				SchedulingMechanisms.preemptRunningProcess();
				SchedulingMechanisms.dispatchProcess(priorityQ.poll(), -1);		
			}
		}
	}
}
