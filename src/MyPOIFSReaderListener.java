import org.apache.poi.hpsf.PropertySetFactory;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;

class MyPOIFSReaderListener implements POIFSReaderListener
{
    public void processPOIFSReaderEvent(POIFSReaderEvent event)
    {
        SummaryInformation si = null;
        try
        {
            si = (SummaryInformation)
                 PropertySetFactory.create(event.getStream());
        }
        catch (Exception ex)
        {
            throw new RuntimeException
                ("Property set stream \"" +
                 event.getPath() + event.getName() + "\": " + ex);
        }
        final String title = si.getTitle();
        if (title != null)
            System.out.println("Title: \"" + title + "\"");
        else
            System.out.println("Document has no title.");
    }
}