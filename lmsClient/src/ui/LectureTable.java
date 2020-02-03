package ui;

import java.util.Vector;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.ELecture;
import main.CSocket;
import ui.SelectionPanel.SelectionMouseHandler;
import view.VLecture;

public class LectureTable extends JTable {
	private static final long serialVersionUID = 1L;
	private  Vector<String> listData;     	// model data
	private Vector<ELecture> eLectures;
	private DefaultTableModel tableModel;
	private CSocket cSocket;
	public LectureTable(SelectionMouseHandler mouseHandler, CSocket cSocket) {
		this.cSocket=cSocket;
		String header[] = {"강좌이름","교수님","학점","강좌시간"};
		
		this.tableModel = new DefaultTableModel(header,0) {            //편집불가
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
		};

		this.setModel(tableModel);
		this.getColumn("강좌이름").setPreferredWidth(100);
		this.getColumn("교수님").setPreferredWidth(10);
		this.getColumn("학점").setPreferredWidth(10);
		this.getColumn("강좌시간").setPreferredWidth(200);
		
		this.addMouseListener(mouseHandler);
	}
	public void refresh(String fileName) {
		if(fileName != null) {
			this.eLectures = new Vector<ELecture>();
			VLecture vLecture = new VLecture(cSocket);
			this.eLectures = vLecture.run(fileName);
			this.tableModel.setRowCount(0);//전 데이터 삭제
			for(ELecture eLecture : eLectures) {
				this.listData = new Vector<String>();
				this.listData.add(eLecture.getName());
				this.listData.add(eLecture.getProfessorName());
				this.listData.add(eLecture.getCredit());
				this.listData.add(eLecture.getTime());
				this.tableModel.addRow(listData);
			}
			
		}

	}
	public Vector<ELecture> eLecture(){
		return this.eLectures;
	}
	
	public Vector<ELecture> getSelectedLectures(){
		return null;
	}
}
