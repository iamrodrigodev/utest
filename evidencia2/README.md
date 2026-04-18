# Pruebas Unitarias con Mockito

## Prueba
Se implementaron casos de prueba para estas clases:

- `BinaryTree`
- `CircleLinkedList`
- `DynamicArray`

Cobertura validada en los tests:

- Inserción, búsqueda y eliminación en árbol binario.
- Recorrido `inOrder` verificando impresión con Mockito (`PrintStream` mockeado).
- Operaciones de append/remove y validación de errores en lista circular.
- Operaciones add/get/put/remove/isEmpty en arreglo dinámico.
- Verificación con Mockito de `forEachRemaining` usando un `Consumer` mockeado.

## Comando de la prueba
Ejecutar en la raíz del proyecto (`evidencia2`):

```bash
mvn test
```

Resultado esperado: `BUILD SUCCESS` y `Tests run: 13, Failures: 0, Errors: 0`.

## Imagen a colocar
Coloca la captura del resultado de pruebas en:

`docs/img/test-run.png`

Y referencia la imagen así en este README:

```markdown
![Resultado de pruebas](docs/img/test-run.png)
```

Ejemplo:

![Resultado de pruebas](docs/img/test-run.png)
