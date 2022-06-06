package ru.netology.graphics;

import ru.netology.graphics.image.Converter;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {

        TextGraphicsConverter converter = new Converter(); // Создайте тут объект вашего класса конвертера

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
//        String url = "https://wallpapershome.com/images/wallpapers/panda-3840x2160-cute-animals-4k-14878.jpg";
//        converter.setMaxHeight(300);
//        converter.setMaxWidth(300);
//        converter.setMaxRatio(4);
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);

    }
}