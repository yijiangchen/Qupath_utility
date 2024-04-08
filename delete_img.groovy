//put this groovy file in your qupath scripts folders, and go to automate -> run.
import static qupath.lib.gui.scripting.QPEx.*


fh = new File("D:/PTC_study_WSIs/project_new/remove.csv")// location of file containing 1 column of list of images to be removed.
def csv_content = fh.grep()


def project = getProject()
//for (entry in project.getImageList()) {
//    print entry.getImageName()
//}
for (line in csv_content) {
   //currentImageData == line.replace("\"", "")
   image_to_be_removed = line.replace("\"", "")
   //print image_to_be_removed
   def entry = project.getImageList().find { it.getImageName() == image_to_be_removed}
   
   try{def imageData = entry.readImageData()
   
   print imageData
   project.removeImage(entry,true)}
   catch(NullPointerException e)   { 
            System.out.print("Caught NullPointerException"); 
   getQuPath().refreshProject()
}
}