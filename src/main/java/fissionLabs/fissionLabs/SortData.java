package fissionLabs.fissionLabs;

import java.util.Comparator;

public class SortData {
	
	public static class OrgnizationSorter implements Comparator<EmployeeBean>
	{
	    public int compare(EmployeeBean o1, EmployeeBean o2)
	    {
	        return o1.getOrganization().compareTo(o2.getOrganization());
	    }
	}
	
	public static class ExperianceSorter implements Comparator<EmployeeBean>
	{
	    public int compare(EmployeeBean o1, EmployeeBean o2)
	    {
	        return o1.getExperience().compareTo(o2.getExperience());
	    }
	}
	
	public static class FirstNameSorter implements Comparator<EmployeeBean>
	{
	    public int compare(EmployeeBean o1, EmployeeBean o2)
	    {
	        return o1.getFirstName().compareTo(o2.getFirstName());
	    }
	}
	
	public static class LastNameSorter implements Comparator<EmployeeBean>
	{
	    public int compare(EmployeeBean o1, EmployeeBean o2)
	    {
	        return o1.getLastName().compareTo(o2.getLastName());
	    }
	}
	
	public static class RatioSorter implements Comparator<EmployeeBean>
	{
	    public int compare(EmployeeBean o1, EmployeeBean o2)
	    {
	        return Double.compare(o1.getRatio(), o2.getRatio());
	    }
	}

}
