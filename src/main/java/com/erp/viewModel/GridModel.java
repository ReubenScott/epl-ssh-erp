package com.erp.viewModel;

import java.util.ArrayList;
import java.util.List;

public class GridModel
{
	private List<?>  rows= new ArrayList<Object>();
	private Long total=0L;
	public List<?> getRows()
	{
		return rows;
	}
	public void setRows(List<?> rows)
	{
		this.rows = rows;
	}
	public Long getTotal()
	{
		return total;
	}
	public void setTotal(Long total)
	{
		this.total = total;
	}
	
	
}
