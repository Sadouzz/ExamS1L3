package sn.brasilburger.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import javax.swing.*;
import java.io.File;
import java.util.Map;

public class CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryService() {

        cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", "drnuhuga3",
                "api_key", "488384562897654",
                "api_secret", "JG9QJYk04rwzuBoSI1UuwkXIsCU"
        ));
    }

    public String uploadImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Sélectionner une image");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.addChoosableFileFilter(
                new javax.swing.filechooser.FileNameExtensionFilter(
                        "Images", "jpg", "jpeg", "png", "webp"
                )
        );

        int result = chooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            System.out.println("Fichier sélectionné : " + selectedFile.getAbsolutePath());

            try {
                Map uploadResult = cloudinary.uploader().upload(
                        selectedFile,
                        ObjectUtils.emptyMap()
                );

                String url = uploadResult.get("secure_url").toString();
                System.out.println("Image uploadée avec succès !");
                System.out.println("URL Cloudinary : " + url);
                return url;

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erreur lors de l'upload : " + e.getMessage());
                return null;
            }

        } else {
            System.out.println("Sélection annulée.");
            return null;
        }
    }
}
