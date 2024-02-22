import numpy as np
import os
from  PIL import Image
import matplotlib.pyplot as plt
import cv2
import json
import platform

from Metoda import *
from Problem import *

def clear_terminal():
    system_name = platform.system()
    if system_name == 'Windows':
        os.system('cls')
    else:
        os.system('clear')
        
def param_edit():
    while True:
        print("Uwaga. Zmiana parametrów może mieć skutek dopiero po ponownej inicjalizacji modelu, lub ponownym załadowaniu obrazów.")
        for index, (opis, _) in params.items():
            if index != 9:
                print(f"{index}) {opis} = {globals()[params[index][1]]}")
            else:
                print(f"{index}) {opis}")

        try:
            wybor = int(input("Wybierz numer parametru do edycji (lub 9 aby zakończyć): "))
        except:
            print("Błąd wyboru")
        else:
            if wybor == 9:
                break
            elif wybor in params and wybor != 9:
                nowa_wartosc = input(f"\nPodaj nową wartość dla {params[wybor][0]}: ")
                try:
                    globals()[params[wybor][1]] = eval(nowa_wartosc)
                    print(f"{params[wybor][0]} ustawiono na {globals()[params[wybor][1]]}")
                except:
                    print(input("Wprowadzono nieprawidłową wartość. Wciśnij enter."))
            else:
                print("Nieprawidłowy wybór.")
        finally:
            clear_terminal()
            
params = {
    1: ('Liczba epok', 'EPOCHS'),
    2: ('Rozmiar batcha', 'BATCH_SIZE'),
    3: ('Learning rate', 'LEARNING_RATE'),
    4: ('Weight decay L2', 'WEIGHT_DECAY'),
    5: ('SGD momentum', 'SGD_MOMENTUM'),
    6: ('Wartość odcinania gradientów clip value', 'CLIP_VALUE'),
    7: ('Wyokośc obrazu', 'img_height'),
    8: ('Szerokość obrazu', 'img_width'),
    9: ('Powrót', None)
}


def main():
    global model
    while True:
        clear_terminal()
        print("\nMenu:")
        print("1. Ponowna inicjalizacja modelu")
        print('2. Ustawienia parametrów')
        print("3. Trenuj model")
        print("4. Przetwórz zdjęcie")
        print("5. Załąduj zdjęcia")
        print("6. Prezentacja wyników")
        print("7. Eksport modelu")
        print("8. Import modelu")
        print("9. Wyjście")

        choice = input("Wybierz opcję: ")

        if choice == '1':
            if input("\nCzy na pewno? Wszystkie wagi zostaną zainicjalizowane na nowo. [Y/n]") in 'Yy':
                try:
                    model = CNNAutoencoder(learning_rate=LEARNING_RATE, momentum=SGD_MOMENTUM, weight_decay=WEIGHT_DECAY, clip_value=CLIP_VALUE)
                    print(input("Inicjaliza zakończona sukcesem. Wciśnij enter by powrócić."))
                except Exception as e:
                    print(input(f"Błąd podczas inicjalizacji. Treść błędu: \n>{e}\nNaciśnij enter aby powrócić."))
            pass
        
        elif choice == '2':
            clear_terminal()
            param_edit()
            pass

        elif choice == '3':
            try:
                x1, x2, y1, y2 = X_TRAIN.shape[0], Y_TRAIN.shape[0], X_VALID.shape[0], Y_VALID.shape[0]
                if x1>0 and x2>0 and y1>0 and y2>0 and x1==x2 and y1==y2:
                    print(f"Aktualne parametry:\n\tLiczba epok: {EPOCHS}\n\tRozmiar batcha: {BATCH_SIZE}\n\tSGD moment: {SGD_MOMENTUM}\n\tWeight decay L2: {WEIGHT_DECAY}\n\tWartość obcinania clipa value: {CLIP_VALUE}") 
                    if input("Czy chcesz rozpocząć trenowanie modelu? [Y/N]") in "Yy":
                        clear_terminal()
                        print("Trwa trenowanie...")
                        model.set_training_mode(True)
                        loss, val_losses, _ = model.train(X_TRAIN, Y_TRAIN, X_VALID, Y_VALID, epochs= EPOCHS, batch_size= BATCH_SIZE)
                        model.set_training_mode(False)
                        print(input("Trening zakończony sukcesem. Nacisnij enter aby kontynuoować."))
                else:
                    print("Błąd załadowania obrazów treningowych lub walidyjnych.")
                pass
            except Exception as e:
                print(input(f"Błąd odczytu zdjęć. Treść błędu: \n>{e}\nWciśnij enter aby powrócić."))

        elif choice == '4':
            try:
                x1, x2, y1, y2, z1, z2 = X_TRAIN.shape[0], Y_TRAIN.shape[0], X_VALID.shape[0], Y_VALID.shape[0], X_TEST.shape[0], Y_TEST.shape[0]
            except Exception as e:
                print(input(f"Nie załadowano obrazów. Treść błędu: \n>{e}\n Wciśnij enter by powrócić."))
            else:
                if x1==x2 and y1==y2 and z1==z2 and x1>0 and y1>0 and z1>0:
                    clear_terminal()
                    set_choice = input("Z jakiego zestawu chcesz przetworzyć obraz?\n\t1. Obrazy treningowe.\n\t2. Obrazy walidacyjne.\n\t3. Obrazy testowe.\n\n\tWybierz dowolny inny znak aby powrócić.\n")
                    
                    if set_choice=='1':
                        x_process = X_TRAIN
                        y_process = Y_TRAIN
                    elif set_choice=='2':
                        x_process = X_VALID
                        y_process = Y_VALID
                    elif set_choice=='3':
                        x_process = X_TEST
                        y_process = Y_TEST
                    else:
                        continue
                    
                    print("Trwa przetwarzanie.")
                    try:
                        processed = model.forward(x_process)
                    except Exception as e:
                        print(input(f"Błąd przetwarzania. Treść błędu: \n>{e}\nNaćiśnij enter aby powrócić."))
                    else:
                        clear_terminal()
                        while True:
                            clear_terminal()
                            index_img = (input(f"Wybierz indeks zdjęcia z zakresu 0 : {x_process.shape[0]-1}\nLub dowolny inny znak aby powrócić.\n"))
                            try:
                                index_img = int(index_img)
                            except:
                                break
                            if x_process.shape[0] < index_img <= 0:
                                break
                            else:
                                index_mask = (input("Wybierz maskę do wyświetlenia z zakresu 0 : 12\n\t0   TŁO\n\t1   BUDYNKI\n\t2   OGRODZENIA // PŁOTY // BARIERKI"
                                                    "\n\t3   ELEMENTY BUDYNKÓW\n\t4   PIESI\n\t5   SŁUPY // LATARNIE\n\t6   ZNAKI POZIOME\n\t7   JEZDNIA"
                                                    "\n\t8   CHODNIK\n\t9   ROŚLINNOŚĆ\n\t10  POJAZDY\n\t11  SYGNALIZACJA ŚWIETLNA"
                                                    "\nWybierz dowolny inny znak aby powrócić\n"))
                                try:
                                    index_mask = int(index_mask)
                                except:
                                    break
                                if 0 <= index_img <= 11:
                                    try:
                                        display_images(y_process, processed, index_img, index_mask)
                                        clear_terminal()
                                    except Exception as e:
                                        print(input(f"Błąd: \n>{e}\nWciśnij enter aby kontynuuować."))
                else:
                    print(input("Błąd odczytu zdjęć. Naciśnij enter aby powrócić."))
            finally:
                clear_terminal()
                    
        elif choice == '5':
            try:
                X_TRAIN = load_images_from_folder('images\\train\\img', target_size=(img_width, img_height))
                Y_TRAIN = load_segments_from_folder('images\\train\\seg', target_size=(img_width, img_height))
                if X_TRAIN.shape[0] == Y_TRAIN.shape[0]:
                    print("Załadowano obrazy treningowe: ", X_TRAIN.shape[0])
                else:
                    print("Błąd podczas odczytu zdjęć treningowych.")
                X_VALID = load_images_from_folder('images\\validation\\img', target_size=(img_width, img_height))
                Y_VALID = load_segments_from_folder('images\\validation\\seg', target_size=(img_width, img_height))
                if X_VALID.shape[0] == Y_VALID.shape[0]:
                    print("Załadowano obrazy walidacyjne: ", X_VALID.shape[0])
                else:
                    print("Błąd podczas odczytu zdjęć walidacyjncyh.")
                X_TEST = load_images_from_folder('images\\test\\img', target_size=(img_width, img_height))
                Y_TEST = load_segments_from_folder('images\\test\\seg', target_size=(img_width, img_height))
                if X_TEST.shape[0] == Y_TEST.shape[0]:
                    print("Załadowano obrazy testowe: ", X_TEST.shape[0])
                else:
                    print("Błąd podczas odczytu zdjęć testowych.")
            except Exception as e:
                print(input(f"Błąd odczytu zdjęć. Treść błędu: \n>{e}\nNaciśnij enter."))
            else:
                print(input(f"\nOdczyt zakończonu sukcesem. Naciśnij enter aby powrócić."))

        elif choice == '6':
            try:
                plt.plot(loss, label='Training Loss')
                plt.plot(val_losses, label='Validation Loss')

                plt.legend()
                
                plt.title('Training and Validation Loss')
                plt.xlabel('Epochs')
                plt.ylabel('Loss')

                plt.show()
            except Exception as e:
                print(input(f"Błąd podczas próby wyświetlenia. Treść błędu:\n>{e}\nNaciśnij enter aby powrócić."))
        
        elif choice == '7':
            try:
                model.export_weights('weights')
                print(input(f"Zapis wag zakończony sukcesem. \nWciśnij enter."))
            except Exception as e:
                print(input(f"Błąd eksportu modelu.Treść błędu:\n{e}\nWciśnij enter aby powrócić."))
        
        elif choice == '8':
            try:
                model.import_weights("weights")
                print(input(f"Odczyt wag zakończony sukcesem. \nNaciśnij enter aby powrócić."))
            except Exception as e:
                print(input(f"Błąd importu modelu. Treść błędu:\n>{e}\nWciśnij enter aby powrócić."))
                
        elif choice == '9':
            break
        
        else:
            print("Nieznana opcja")
            
        
        
        
if __name__ == "__main__":
    
    LEARNING_RATE = .001
    SGD_MOMENTUM = .9
    WEIGHT_DECAY = .0001
    CLIP_VALUE = 0
    EPOCHS = 10
    BATCH_SIZE = 5
    img_height = 192
    img_width = 256
    model = CNNAutoencoder(learning_rate=LEARNING_RATE, momentum=SGD_MOMENTUM, weight_decay=WEIGHT_DECAY, clip_value=CLIP_VALUE)
    
    main()

print(input("\n\n\n\nError handler. Naciśnij enter."))