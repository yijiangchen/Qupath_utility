import qupath.lib.images.servers.LabeledImageServer

def imageData = getCurrentImageData()

// Define output path (here, relative to project)
def name = GeneralTools.getNameWithoutExtension(imageData.getServer().getMetadata().getName())
//def pathOutput = buildFilePath('D:/IFTA_segmentation/', 'tiles_2_classes_with_cortex', name)

def pathOutput = buildFilePath('Z:/yxc627/IFTA_tiles_multi_channel/', name)
mkdirs(pathOutput)

// Define output resolution
double requestedPixelSize = 10.0

// Convert to downsample
double downsample = 1.0

// Create an ImageServer where the pixels are derived from annotations
def labelServer = new LabeledImageServer.Builder(imageData)
    .backgroundLabel(0, ColorTools.WHITE) // Specify background label (usually 0 or 255)
    .downsample(downsample)    // Choose server resolution; this should match the resolution at which tiles are exported
    .addLabel('Other', 1)
    .addLabel('Positive', 2)     // Choose output labels (the order matters!)
    .addLabel('Ignore*', 3) 
    .multichannelOutput(true)  // If true, each label is a different channel (required for multiclass probability)
    .build()

// Create an exporter that requests corresponding tiles from the original & labeled image servers
new TileExporter(imageData)
    .downsample(downsample)     // Define export resolution
    .imageExtension('.png')     // Define file extension for original pixels (often .tif, .jpg, '.png' or '.ome.tif')
    .tileSize(3000)              // Define size of each tile, in pixels
    .labeledServer(labelServer) // Define the labeled image server to use (i.e. the one we just built)
    .annotatedTilesOnly(true)  // If true, only export tiles if there is a (labeled) annotation present
    .overlap(0)                // Define overlap, in pixel units at the export resolution
    .writeTiles(pathOutput)     // Write tiles to the specified directory


print 'Done!'