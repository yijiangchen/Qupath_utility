import java.io.File;

String saveDirectory = 'D:/IFTA_WSI_label_json/' 
def imageData = getCurrentImageData()

def annotations = getAnnotationObjects()
boolean prettyPrint = true
def gson = GsonTools.getInstance(prettyPrint)
gson.toJson(annotations)

def server = getCurrentImageData().getServer()

String path2 = server.getPath()
int ind1 = path2.lastIndexOf("/") + 1;
int ind2 = path2.lastIndexOf(".") - 1;
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())

String path = buildFilePath(saveDirectory, name +'.json')
//String result = path.replaceAll( "/","\\\\");

new File(path).write(gson.toJson(annotations))