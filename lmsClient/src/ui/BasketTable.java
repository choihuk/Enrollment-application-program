package ui;

import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import entity.ELecture;
import main.CSocket;
import view.VBasket;

public class BasketTable extends JTable {
	private static final long serialVersionUID = 1L;
	private DefaultTableModel model;
	private Vector<ELecture> basket;
	private JOptionPane jOptionPane;
	private int basketRowCount;
	private MouseListener mouseHandler;
	private String userId;
	private CSocket cSocket;

	public BasketTable(String userId, CSocket cSocket) {
		this.userId=userId;
		this.cSocket=cSocket;
		paint();
	}
	public void paint() {
		this.setOpaque(false); 
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
		this.addMouseListener(mouseHandler);

		this.basket = new Vector<ELecture>();
		this.basket = cSocket.getFile("getFile,basket/"+userId+"basket.txt");
		Vector<String> listData;
		for(ELecture baskets : basket) {
			listData = new Vector<String>();
			listData.add(baskets.getName());
			listData.add(baskets.getProfessorName());
			listData.add(baskets.getCredit());
			listData.add(baskets.getTime());
			this.model.addRow(listData);
		}
		this.basketRowCount = this.getRowCount();
	}

	public void MouseClickedAction(SincheongTable sincheongTable) {
		Vector<Integer> count = new Vector<Integer>();
		for(int a=0;this.getRowCount()>=a;a++) {
			if(this.isRowSelected(a)) {
				count.add(a);
			}
		}
		for(int b=0;count.size()>b;b++) {
			int row=count.get(b);
			String Name = basket.get(row).getName();
			String ProfessorName = basket.get(row).getProfessorName();
			String Credit = basket.get(row).getCredit();
			String Time = basket.get(row).getTime();
			String line = "basket/"+userId+"sincheong.txt_"+Name+"_"+ProfessorName+"_"+Credit+"_"+Time;
			VBasket vBasket = new VBasket(cSocket);
			boolean isWrite = vBasket.run(line);
			if(isWrite) {
				JOptionPane.showConfirmDialog(null,Name+" 강좌가 중복되었습니다.","메시지", JOptionPane.PLAIN_MESSAGE  );
			}
		}

	}
	public String basketRowCount() {
		String a = Integer.toString(basketRowCount);
		return a;
	}

}
