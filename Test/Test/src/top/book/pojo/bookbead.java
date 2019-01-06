package top.book.pojo;

import java.util.List;

public class bookbead {
	private String total;
	private List<book> rows;

	public void setRows(List<book> books) {
		this.rows = books;
	}

	public List<book> getRows() {
		return rows;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

}
