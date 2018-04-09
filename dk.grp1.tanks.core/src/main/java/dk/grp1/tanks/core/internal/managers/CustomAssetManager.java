package dk.grp1.tanks.core.internal.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class CustomAssetManager {

    private AssetManager assetManager;
    private Map<String, String> tempFileMap;
    private String localStoragePath;

    public CustomAssetManager(String localStoragePath) {
        this.assetManager = new AssetManager();
        this.tempFileMap = new HashMap<>();
        this.localStoragePath = localStoragePath;
    }

    /**
     * Creates a new temporary file from the given Class' ClassLoader and FileName.
     * @param clazz
     * @param fileName
     * @param fileType The File type of the file. This can be .mp3, .wav, etc.
     * @return
     */
    private File createTempFileFromBundle(Class clazz, String fileName, String fileType) {
        InputStream is = clazz.getClassLoader().getResourceAsStream(fileName+fileType);
        File tempFile = null;
        String filePath = this.localStoragePath;
        try {
            tempFile = File.createTempFile(fileName, fileType, new File(filePath));
            tempFile.deleteOnExit(); // Does this even work??

            try (FileOutputStream fos = new FileOutputStream(tempFile)){
                byte[] buffer = new byte[8 * 1024];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, bytesRead);
                }
            }

            System.out.println("TempFile: " + tempFile.getAbsolutePath());
            // Store the file-name/type and the tempFile-path for later lookup
            // Replacing backslashes with forward-slashes for compatibility issues in LibGDX AssetManager.
            tempFileMap.put(fileName+fileType, tempFile.getAbsolutePath().replace("\\", "/"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return tempFile;
    }

    public void loadSoundAsset(Class clazz, String fileName, String fileType) {
        if(tempFileMap.containsKey(fileName+fileType))
            return;
        
        File assetFile = createTempFileFromBundle(clazz, fileName, fileType);

        assetManager.load(assetFile.getAbsolutePath(), Sound.class);

        assetManager.finishLoading();

        if (assetManager.isLoaded(assetFile.getAbsolutePath(), Sound.class)) {
            System.out.println("Loaded sound asset: " + assetFile.getAbsolutePath());
        }

        for (String ass : assetManager.getAssetNames()) {
            System.out.println("AssetManager.getAssetNames()" + ass);
        }
    }

    public Sound getSoundAsset(String fileName, String fileType) {
        String tmpFileName = tempFileMap.get(fileName+fileType);


        Sound sound = null;

        try {

            sound = assetManager.get(tmpFileName);

        } catch (NullPointerException ex) {
            System.out.println("The sound asset: " + fileName+fileType + ", could not be found.");
        }

        return sound;

        //return Gdx.audio.newSound(Gdx.files.absolute(tmpFileName.getAbsolutePath()));
    }
}
