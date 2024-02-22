import numpy as np
import os
from  PIL import Image
import matplotlib.pyplot as plt
import cv2

# funkcja do załadowania do zmiennej zdjęć z dysku
# zdjęcia są normalizowane i konwertowane do float32
def load_images_from_folder(folder, target_size=(400, 300)):
    images = []
    for filename in sorted(os.listdir(folder)): 
        img_path = os.path.join(folder, filename)
        if os.path.isfile(img_path):
            with Image.open(img_path) as img:
                img = img.resize(target_size)
                img = np.array(img) 
                img = img.astype('float32') / 255.0
                images.append(img)
    return np.array(images)

# pomocnicza funkcja do odczyty posegmentowanych masek
# w pierwotnym założeniu zdjęcia posegmentowane są maskami posiadającymi etykiety w kanale czerwonym
# wartość piksela odpowiada danej klasie
def load_segments_from_folder(folder, target_size=(400, 300), num_classes=12):
    images = []
    for filename in sorted(os.listdir(folder)):
        img_path = os.path.join(folder, filename)
        if os.path.isfile(img_path):
            with Image.open(img_path) as img:
                img = img.resize(target_size, Image.NEAREST) 
                img = np.array(img, dtype='uint8')

                if img.ndim > 2:
                    img = img[..., 0]

                img_one_hot = np.zeros((*target_size[::-1], num_classes), dtype='float32') 

                # encoding one hot
                for c in range(num_classes):
                    img_one_hot[..., c] = (img == c).astype('float32')

                images.append(img_one_hot)
    return np.array(images)

#funkcja skąłdająca wszystkie maski z obrazu 12 kanałowego
def combine_masks(masks):
    if masks.ndim == 4:  # Paczka obrazów
        n_images, height, width, n_channels = masks.shape
        combined_masks = np.zeros((n_images, height, width), dtype=np.uint8)
        for i in range(n_images):
            for channel in range(n_channels):
                combined_masks[i][masks[i, :, :, channel] == 1] = channel + 1
    elif masks.ndim == 3:  # Pojedynczy obraz
        height, width, n_channels = masks.shape
        combined_masks = np.zeros((height, width), dtype=np.uint8)
        for channel in range(n_channels):
            combined_masks[masks[:, :, channel] == 1] = channel + 1
    else:
        raise ValueError("Niewłaściwe wymiary wejściowe. Oczekiwano 3 lub 4 wymiarów.")

    return combined_masks

# funkcja wyświetlająca 4 obrazy dla poprawy rpzejrzystości
def display_images(original_images, processed_images, image_index=0, channel_index=0):
    # Złożenie kanałów dla obu obrazów
    combined_original = combine_masks(original_images[image_index])
    combined_processed = combine_masks(processed_images[image_index])

    # Wybór pojedynczych kanałów dla obu obrazów
    single_original = original_images[image_index, :, :, channel_index]
    single_processed = processed_images[image_index, :, :, channel_index]

    # Ustawienie subplots
    fig, axs = plt.subplots(1, 4, figsize=(20, 5))  # Dostosuj rozmiar według potrzeb

    # Wyświetlenie złożonego pierwszego obrazu
    axs[0].imshow(combined_original)
    axs[0].set_title('Złożony obraz oryginalny')
    axs[0].axis('off')  # Wyłączenie osi

    # Wyświetlenie wybranego kanału z pierwszego obrazu
    axs[1].imshow(single_original)
    axs[1].set_title(f'Kanał {channel_index} oryginalnego')
    axs[1].axis('off')

    # Wyświetlenie wybranego kanału z drugiego obrazu
    axs[2].imshow(single_processed)
    axs[2].set_title(f'Kanał {channel_index} obrazu przetworzonego')
    axs[2].axis('off')

    # Wyświetlenie złożonego drugiego obrazu
    axs[3].imshow(combined_processed)
    axs[3].set_title('Złożony obraz przetworzony')
    axs[3].axis('off')

    plt.show()