import json
import time

from requests_html import HTMLSession
from bs4 import BeautifulSoup as bs

def main():
    result = []
    session = HTMLSession()

    url = 'https://msk.kinoafisha.info/'
    res = session.get(url)
    print('Status:', res)
    # print('RESPONSE:', res.text)

    time.sleep(2)
    # Создаем объект BeautifulSoup для парсинга HTML кода страницы
    soup = bs(res.content, 'html.parser')

    # Находим все div элементы с классом "movie-item"
    movie_items = soup.find_all('div', class_='movieList_item movieItem movieItem-grid grid_cell3')

    # Проходим по каждому элементу и извлекаем нужные данные
    for movie_item in movie_items:
        # Название фильма
        title = movie_item.find('a', class_='movieItem_title').text.strip()
        # Ссылка на картинку
        image_url = movie_item.find('picture', class_='movieItem_poster picture picture-poster').find('img', class_='picture_image')['src']
        # Описание фильма
        description = movie_item.find('span', class_='movieItem_genres').text.strip()
        year, country = movie_item.find('span', class_='movieItem_year').text.strip().split(",")
        movie_dict = {
            'title': title,
            'image_url': image_url,
            'description': description,
            'year': year,
            'country': country
        }
        result.append(movie_dict)
    return json.dumps(result)


    # Выводим полученные данные в консоль
    # print("Название фильма:", title)
    # print("Ссылка на картинку:", image_url)
    # print("Описание фильма:", description)
    # print("Год:", year)
    # print("Страна:", country)




