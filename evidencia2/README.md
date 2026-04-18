# Pruebas Unitarias con Mockito

## Objetivo
Implementar y documentar una suite de pruebas unitarias completa para las clases:

- `BinaryTree`
- `CircleLinkedList`
- `DynamicArray`

La suite fue diseñada para cubrir:

- Casos normales (flujo esperado).
- Casos de borde/extremos (límites de índices, estructuras vacías, nodos especiales).
- Casos de error (excepciones y retornos de fallo).
- Ramas críticas de lógica interna (especialmente en `remove` de árbol y en iteradores).
- Verificación de interacciones usando Mockito.

## Enfoque de Diseño de Casos
Se aplicó una estrategia de caja negra funcional + cobertura de ramas relevantes:

1. Validar comportamiento correcto en uso típico.
2. Probar entradas inválidas y estados límite.
3. Forzar rutas internas complejas que suelen generar bugs.
4. Verificar efectos observables (salida, tamaño, enlaces y orden).
5. Usar Mockito para confirmar interacciones (no solo valores de retorno).

## Casos Diseñados por Clase

### 1) `CircleLinkedListTest`
Archivo: `src/test/java/utest/evidencia1/clases/CircleLinkedListTest.java`

#### Casos normales
1. Estado inicial del constructor: tamaño `0` y representación vacía.
2. `append` de varios elementos mantiene orden y aumenta tamaño.
3. `remove` en posición intermedia elimina el elemento correcto y ajusta tamaño.
4. `remove` en primera posición actualiza correctamente la lista.
5. `remove` en última posición actualiza correctamente el final de la lista.

#### Casos de borde y error
6. `append(null)` lanza `NullPointerException`.
7. `remove` con índice negativo lanza `IndexOutOfBoundsException`.
8. `remove` con índice mayor que el tamaño lanza `IndexOutOfBoundsException`.
9. `remove(0)` en lista vacía: se documenta el comportamiento actual (lanza `NullPointerException`).
10. `remove(pos == size)`: se documenta explícitamente el comportamiento actual de la implementación.

### 2) `DynamicArrayTest`
Archivo: `src/test/java/utest/evidencia1/clases/DynamicArrayTest.java`

#### Casos normales
1. Flujo `add/get/put` para validar inserción y actualización.
2. `remove(index)` elimina y desplaza elementos correctamente.
3. `isEmpty` antes y después de insertar.
4. `toString` omite posiciones nulas.
5. `stream()` recorre elementos y permite agregación (suma).
6. Iterador `hasNext/next` en secuencia normal.

#### Casos de borde y error
7. `forEachRemaining(null)` lanza `NullPointerException`.
8. Comportamiento al finalizar iteración (ruta hacia `NoSuchElementException` según implementación actual).
9. `remove()` del iterador y su semántica actual.
10. Doble `remove()` del iterador lanza `IllegalStateException`.
11. Escenario de reducción interna tras múltiples eliminaciones.
12. `forEachRemaining` en estructura vacía no debe invocar el consumidor.
13. Rama de `ConcurrentModificationException` forzada para cubrir la validación interna.

#### Mockito aplicado
14. `forEachRemaining` con `Consumer` mock: se verifica que cada elemento esperado sea consumido exactamente una vez.

### 3) `BinaryTreeTest`
Archivo: `src/test/java/utest/evidencia1/clases/BinaryTreeTest.java`

#### Casos de construcción y búsqueda
1. Constructor por defecto crea árbol vacío.
2. Constructor parametrizado asigna raíz correctamente.
3. `put + find` para valor existente.
4. `find` de valor inexistente retorna nodo padre esperado.
5. `find` en árbol vacío retorna `null`.

#### Casos de eliminación (`remove`) por rama lógica
6. Eliminar hoja.
7. Intentar eliminar valor inexistente retorna `false`.
8. Eliminar en árbol vacío: se documenta el comportamiento actual (`NullPointerException`).
9. Eliminar raíz cuando es hoja (árbol queda vacío).
10. Eliminar hoja no raíz y validar desvinculación del padre.
11. Eliminar raíz con solo hijo derecho.
12. Eliminar raíz con solo hijo izquierdo.
13. Eliminar nodo no raíz con solo hijo derecho.
14. Eliminar nodo no raíz con solo hijo izquierdo.
15. Eliminar raíz con dos hijos (reemplazo por sucesor).
16. Eliminar raíz con dos hijos cuando el sucesor es hijo derecho directo.
17. Eliminar raíz con dos hijos cuando el sucesor tiene hijo derecho.
18. Eliminar nodo no raíz con dos hijos.

#### Casos de sucesor
19. `findSuccessor` normal: mínimo del subárbol derecho.
20. `findSuccessor` cuando no hay hijo derecho (retorna el mismo nodo según implementación actual).

#### Recorridos y salida
21. `inOrder` en orden esperado.
22. `preOrder` en orden esperado.
23. `postOrder` en orden esperado.
24. `bfs` en orden esperado según implementación actual.
25. Recorridos con raíz `null` no imprimen salida.

#### Mockito aplicado
26. Verificación del orden de impresión (`PrintStream` mock) en recorridos.
27. Verificación de ausencia de interacción cuando la raíz es `null`.

## Comando para Ejecutar las Pruebas
Ejecutar en la raíz del proyecto (`evidencia2`):

```bash
mvn test
```

## Resultado Esperado
- `BUILD SUCCESS`
- Todas las pruebas en verde (sin `Failures` ni `Errors`).

## Evidencia (Imagen)
Coloca la captura del resultado de ejecución en:

`docs/img/test-run.png`

Referencia en Markdown:

```markdown
![Resultado de ejecución de pruebas](docs/img/test-run.png)
```

Vista en el README:

![Resultado de ejecución de pruebas](docs/img/test-run.png)
