package com.kam
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.*;
import org.apache.xml.*
class XlsService {

    def serviceMethod() {

    }
    def fetchMe(String fileName){

        Vector arr=read(fileName);
        log.debug("CellVectorHolder.........."+arr)

        List list = saveToDatabase(arr)
        log.debug("Finally List ..........................>>"+list)
        return list

    }

    public Vector read(String fileName)    {

        Vector cellVectorHolder = new Vector();
        try{
            log.debug("Processing.........."+fileName)
            FileInputStream myInput = new FileInputStream(fileName);
            log.debug("Input Stream///"+myInput)
            XSSFWorkbook myWorkBook = new XSSFWorkbook(myInput);
            log.debug("Workbook.........."+myWorkBook)
            XSSFSheet mySheet = myWorkBook.getSheetAt(0);
            log.debug("My Sheet"+mySheet)
            Iterator rowIter = mySheet.rowIterator();
            while(rowIter.hasNext()){
                log.debug("Iterating.............")
                XSSFRow myRow = (XSSFRow) rowIter.next();
                log.debug("Second Cell..................."+myRow.getCell(1,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK))
                log.debug("Last Cell....."+myRow.getLastCellNum())
                Vector cellStoreVector=new Vector();

                for(int i=0;i<myRow.getLastCellNum();i++){
                    cellStoreVector.addElement(myRow.getCell(i,org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK))
                }
                log.debug("Adding...........CellStore as "+cellStoreVector)

               // Iterator cellIter = myRow.cellIterator();
                /*
                while(cellIter.hasNext()){
                    XSSFCell myCell = (XSSFCell) cellIter.next();
                    if(myCell)  {
                        log.debug("Somethingnnnnn")
                        cellStoreVector.addElement(myCell);
                    }
                    else {
                        log.debug("Empty........................................")
                    }

                }
                */
                cellVectorHolder.addElement(cellStoreVector);
            }
        }catch (Exception e){e.printStackTrace();
        log.error("Unable to read"+e)
        }
        return cellVectorHolder;
    }

    ArrayList saveToDatabase(Vector dataHolder) {
        log.debug("Got Vector......"+dataHolder)

        ArrayList arr = new ArrayList()

        for (int i=0;i<dataHolder.size(); i++){
            Vector cellStoreVector=(Vector)dataHolder.elementAt(i);
            if(i==0)
                continue;

            ArrayList my = new ArrayList()

            for (int j=0; j < cellStoreVector.size();j++){

                XSSFCell myCell = (XSSFCell)cellStoreVector.elementAt(j);
                if(myCell)
                    my.add(myCell.toString())


            }
            arr.add(my)

        }
        return arr
    }
}
