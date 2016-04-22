package schedulingalgorithms;

import schedulingsimulation.SchedulingAlgorithm;
import schedulingsimulation.SchedulingMechanisms;
import schedulingsimulation.SimulatedProcess;
import java.util.*;

public class ProcessComparator implements Comparator<SimulatedProcess> {

	// We need to replace the compare() method in the Java Comparator class,
	// much like what we did in the EventQueue.EventComparator class in the
	// SchedulingSimulation.jar file.
	// We can now use this overriden method in our Preemptive Shortest CPU 
	// Burst First class.
	@Override
	public int compare(SimulatedProcess p1, SimulatedProcess p2) {
		Long pr1 = SchedulingMechanisms.getProcessCPUBurstDuration(p1);
		Long pr2 = SchedulingMechanisms.getProcessCPUBurstDuration(p2);
		return (pr1.compareTo(pr2));
	}
}
