# Консольная игра "Морской бой"

## Инструкция по запуску

### Требования
В консоли должна быть установлена кодировка `UTF-8`. 
В *Windows PowerShell* установить `UTF-8` можно следующим образом:
```
    [Console]::OutputEncoding = [System.Text.Encoding]::UTF8
    [Console]::InputEncoding = [System.Text.Encoding]::UTF8
```
### Запуск
1. Скачайте архив с репозиторием в удобное место у себя на компьютере:
    ```
    git clone https://github.com/GhostOfEndless/battleship-console-game.git
    ```
2. Далее перейдите в директорию проекта:
    ```
    cd battleship-console-game
    ```
3. Теперь можно запустить приложение:
      ```
      java -jar battleship-console-game.jar
      ```