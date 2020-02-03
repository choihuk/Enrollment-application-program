package ui;

import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.ELecture;
import main.CSocket;
import view.VSincheong;

public class SincheongTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private int SinRowCount;
	private int Credit;
	private String userId;
	private CSocket cSocket;
	
	public SincheongTable(String userId, CSocket cSocket) {
		this.userId = userId;
		this.cSocket=cSocket;
		paint();
	}
	public void paint() {
		String header[] = {"강좌이름","교수님","학점","강좌시간"};
		this.model = new DefaultTableModel(header,0) {            //편집불가
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		        return false;
		    }
		};
		this.setModel(model);
		this.getColumn("강좌이름").setPreferredWidth(100);
		this.getColumn("교수님").setPreferredWidth(10);
		this.getColumn("학점").setPreferredWidth(10);
		this.getColumn("강좌시간").setPreferredWidth(200);
		
		Vector<ELecture> basket = new Vector<ELecture>();
		VSincheong vSincheong = new VSincheong(cSocket);
		basket = vSincheong.run("getFile,basket/"+userId+"sincheong.txt");
		this.Credit = 0;
		Vector<String> listData;
		for(ELecture baskets : basket) {
			if(Credit+Integer.parseInt(baskets.getCredit())<=18) {
			listData = new Vector<String>();
			listData.add(baskets.getName());
			listData.add(baskets.getProfessorName());
			listData.add(baskets.getCredit());
			listData.add(baskets.getTime());
			int a = Integer.parseInt(baskets.getCredit());
			Credit += a;
			this.model.addRow(listData);
			}else {
				JOptionPane.showConfirmDialog(null,"학점이 초과되었습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
				basket=null;
			}
		}
		
		SinRowCount = this.getRowCount();		
		
	}
	public String sinRowCount() {
		String a = Integer.toString(SinRowCount);
		return a;
	}
	public String credit() {
		String a = Integer.toString(Credit);
		return a;
	}

}
