#!/bin/bash
# ========================================================================================
# SCRIPT WRAPPER PARA EJECUTAR EL INICIALIZADOR DE TEMPLATE
# ========================================================================================
# Este script ejecuta el Kotlin Script usando el Kotlin Compiler standalone
# ========================================================================================

set -e

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Obtener el directorio del script y la raíz del proyecto
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "$SCRIPT_DIR/.." && pwd )"

# Cambiar al directorio raíz del proyecto
cd "$PROJECT_ROOT" || exit 1

echo -e "${CYAN}🚀 Inicializando el template de proyecto Android...${NC}"
echo ""

# Verificar si kotlin está instalado
if ! command -v kotlin &> /dev/null; then
    echo -e "${YELLOW}⚠️  Kotlin no está instalado en el sistema.${NC}"
    echo -e "${CYAN}Ejecutando con el JDK del proyecto...${NC}"
    echo ""

    # Intentar ejecutar usando el JDK del proyecto con kotlinc
    if [ -f "./gradlew" ]; then
        # Usar kotlinc si está disponible en el PATH
        if command -v kotlinc &> /dev/null; then
            kotlinc -script "$SCRIPT_DIR/init-project.main.kts"
        else
            echo -e "${RED}❌ Error: kotlinc no está disponible.${NC}"
            echo ""
            echo -e "${YELLOW}Opciones para ejecutar este script:${NC}"
            echo "  1. Instalar Kotlin: https://kotlinlang.org/docs/command-line.html"
            echo "  2. Ejecutar manualmente el script desde IntelliJ/Android Studio"
            echo "  3. Copiar el código del script y ejecutarlo en Kotlin Playground"
            echo ""
            echo -e "${CYAN}Ubicación del script: ${SCRIPT_DIR}/init-project.main.kts${NC}"
            exit 1
        fi
    else
        echo -e "${RED}❌ Error: No se puede ejecutar el script.${NC}"
        exit 1
    fi
else
    # Ejecutar el script de Kotlin
    kotlin "$SCRIPT_DIR/init-project.main.kts"
fi

