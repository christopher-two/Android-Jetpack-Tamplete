#!/bin/bash
# ========================================================================================
# SCRIPT DE VERIFICACIÓN DE PROGUARD
# ========================================================================================
# Este script verifica que la configuración de ProGuard funcione correctamente
# ========================================================================================

echo "🔍 Verificando configuración de ProGuard..."
echo ""

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Función para verificar si un comando existe
command_exists() {
    command -v "$1" >/dev/null 2>&1
}

# Obtener el directorio del script y la raíz del proyecto
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "$SCRIPT_DIR/.." && pwd )"

# Cambiar al directorio raíz del proyecto
cd "$PROJECT_ROOT" || exit 1

echo "📁 Directorio del proyecto: $PROJECT_ROOT"
echo ""

# Verificar que estamos en el directorio correcto
if [ ! -f "app/proguard-rules.pro" ]; then
    echo -e "${RED}❌ Error: No se encuentra app/proguard-rules.pro${NC}"
    echo "Estructura del proyecto incorrecta"
    exit 1
fi

echo -e "${GREEN}✅ Archivo proguard-rules.pro encontrado${NC}"

# Verificar que Gradle esté disponible
if [ ! -f "./gradlew" ]; then
    echo -e "${RED}❌ Error: gradlew no encontrado${NC}"
    exit 1
fi

echo -e "${GREEN}✅ Gradle wrapper encontrado${NC}"
echo ""

# Limpiar builds anteriores
echo "🧹 Limpiando builds anteriores..."
./gradlew clean > /dev/null 2>&1
echo -e "${GREEN}✅ Limpieza completada${NC}"
echo ""

# Compilar en modo release
echo "🔨 Compilando en modo Release con ProGuard..."
echo -e "${YELLOW}⏳ Esto puede tardar varios minutos...${NC}"
echo ""

if ./gradlew assembleRelease; then
    echo ""
    echo -e "${GREEN}✅ Compilación exitosa!${NC}"
    echo ""

    # Verificar que los archivos de mapping existen
    if [ -f "app/build/outputs/mapping/release/mapping.txt" ]; then
        echo -e "${GREEN}✅ Archivo mapping.txt generado${NC}"

        # Mostrar estadísticas
        echo ""
        echo "📊 Estadísticas de ProGuard:"
        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"

        # Contar clases ofuscadas
        MAPPED_CLASSES=$(grep -c " -> " app/build/outputs/mapping/release/mapping.txt)
        echo "   Clases procesadas: $MAPPED_CLASSES"

        # Tamaño de la APK
        if [ -f "app/build/outputs/apk/release/app-release-unsigned.apk" ] || [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
            APK_FILE=$(find app/build/outputs/apk/release/ -name "*.apk" | head -1)
            APK_SIZE=$(du -h "$APK_FILE" | cut -f1)
            echo "   Tamaño de APK: $APK_SIZE"
        fi

        echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    fi

    # Verificar archivos de salida opcionales
    echo ""
    echo "📁 Archivos de salida generados:"

    if [ -f "app/build/outputs/mapping/release/seeds.txt" ]; then
        echo -e "   ${GREEN}✅${NC} seeds.txt (clases mantenidas)"
    else
        echo -e "   ${YELLOW}⚠️${NC}  seeds.txt (no generado)"
    fi

    if [ -f "app/build/outputs/mapping/release/usage.txt" ]; then
        echo -e "   ${GREEN}✅${NC} usage.txt (clases eliminadas)"
    else
        echo -e "   ${YELLOW}⚠️${NC}  usage.txt (no generado)"
    fi

    if [ -f "app/build/outputs/mapping/release/configuration.txt" ]; then
        echo -e "   ${GREEN}✅${NC} configuration.txt (configuración usada)"
    else
        echo -e "   ${YELLOW}⚠️${NC}  configuration.txt (no generado)"
    fi

    echo ""
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo -e "${GREEN}✨ Verificación completada con éxito!${NC}"
    echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
    echo ""
    echo "📝 Siguiente paso:"
    echo "   Instala y prueba la APK en un dispositivo:"
    echo -e "   ${YELLOW}adb install app/build/outputs/apk/release/app-release.apk${NC}"
    echo ""

else
    echo ""
    echo -e "${RED}❌ Error en la compilación${NC}"
    echo ""
    echo "🔍 Revisa los errores anteriores."
    echo "💡 Si ves errores de ProGuard, verifica app/proguard-rules.pro"
    echo ""
    exit 1
fi

