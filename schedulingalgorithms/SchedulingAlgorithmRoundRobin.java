package schedulingalgorithms;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;
import java.util.*;

/*
* Blake Larson
*
* Round Robin:
*	Allows processes to run for for a certain time slice (in this case 10),
*	when they are finished, the program will check a ready queue, from which
*	it pulls from. Any process that are ready but another is running, will fall
*	into this ready queue and wait for its turn.
*/

public class SchedulingAlgorithmRoundRobin implements SchedulingAlgorithm {
	// Create the ready queue
	Queue<SimulatedProcess> readyQueue = new LinkedList();

	public SchedulingAlgorithmRoundRobin() {
		// Whatever initialization here for data structures
		// Needed by the scheduler (nothing for the Bogus scheduler)
	}

	
	public void handleCPUBurstCompletionEvent(SimulatedProcess process) {
		// Check if the ready queue is empty first
		if (!readyQueue.isEmpty()) {
			// Run the next process on the top of the ready queue
			SchedulingMechanisms.dispatchProcess(readyQueue.poll(), 10);
		}
	}

	public void handleExpiredTimeSliceEvent(SimulatedProcess process) {
		// When time is up, dispatch a new process from the ready queue
		if (!readyQueue.isEmpty()) {
			SchedulingMechanisms.dispatchProcess(readyQueue.poll(),10);
		}
	}

	public void handleProcessReadyEvent(SimulatedProcess process) {	
		// When a process becomes ready, I check if nobody is running, 
		// and in this case I dispatch the process with a 10 time slice
		if (SchedulingMechanisms.getRunningProcess() == null) {
			SchedulingMechanisms.dispatchProcess(process, 10);
		}
		// When a process becomes ready, I check if nobody is running,
		// and in this case I can't run yet, so I get in line
		else if (SchedulingMechanisms.getRunningProcess() != null) {
			readyQueue.offer(process);
		}
	}
}
