package Model;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class tableModel extends AbstractTableModel {
    private static int columnCount = 3;
    private ArrayList<String[]> dataArrayList;
    @Override
    public int getRowCount() {
        return dataArrayList.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }
    public String getColumnCount(int columnIndex) {
        switch (columnIndex){
            case 0: return "id";
            case 1: return "nazv";
            case 2: return "avtor";
        }
        return null;
    }
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] row = dataArrayList.get(rowIndex);
        return row[columnIndex];
    }
    public String[] whatColNames(ResultSet set) throws SQLException {
        ResultSetMetaData mdata = set.getMetaData();
        String[] colNames = new String[mdata.getColumnCount()];
        for (int i=0; i<mdata.getColumnCount(); i++) colNames[i] = mdata.getColumnName(i+1);
        return colNames;
    }
    public tableModel() {
        dataArrayList = new ArrayList<String[]>();
        for(int i = 0;i < dataArrayList.size();i++){
            dataArrayList.add(new String[getColumnCount()]);
        }
    }
}
