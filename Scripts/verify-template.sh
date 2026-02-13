#!/bin/bash
# ========================================================================================
# SCRIPT DE VERIFICACIÓN DEL INICIALIZADOR
# ========================================================================================
# Este script verifica que el inicializador funcione correctamente
# ========================================================================================

set -e

# Colores
GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

echo -e "${CYAN}╔═══════════════════════════════════════════════════════════════╗${NC}"
echo -e "${CYAN}║         VERIFICACIÓN DEL INICIALIZADOR DE TEMPLATE           ║${NC}"
echo -e "${CYAN}╚═══════════════════════════════════════════════════════════════╝${NC}"
echo ""

# Obtener el directorio del script y la raíz del proyecto
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_ROOT="$( cd "$SCRIPT_DIR/.." && pwd )"

cd "$PROJECT_ROOT" || exit 1

echo -e "${CYAN}📋 Verificando archivos del template...${NC}"
echo ""

# Verificar que existen los archivos necesarios
REQUIRED_FILES=(
    "Scripts/init-project.main.kts"
    "Scripts/init-project.sh"
    "project-config.template"
    "settings.gradle.kts"
    "app/build.gradle.kts"
    "app/src/main/AndroidManifest.xml"
    "app/src/main/res/values/strings.xml"
)

ALL_OK=true

for file in "${REQUIRED_FILES[@]}"; do
    if [ -f "$file" ]; then
        echo -e "${GREEN}✅${NC} $file"
    else
        echo -e "${RED}❌${NC} $file ${RED}(NO ENCONTRADO)${NC}"
        ALL_OK=false
    fi
done

echo ""

# Verificar valores por defecto del template
echo -e "${CYAN}🔍 Verificando valores por defecto del template...${NC}"
echo ""

# Verificar settings.gradle.kts
if grep -q 'rootProject.name = "Tamplete"' settings.gradle.kts; then
    echo -e "${GREEN}✅${NC} settings.gradle.kts contiene el nombre por defecto"
else
    echo -e "${RED}❌${NC} settings.gradle.kts - nombre por defecto no encontrado"
    ALL_OK=false
fi

# Verificar app/build.gradle.kts
if grep -q 'val nameProject = "tamplete"' app/build.gradle.kts; then
    echo -e "${GREEN}✅${NC} app/build.gradle.kts contiene la variable nameProject"
else
    echo -e "${RED}❌${NC} app/build.gradle.kts - variable nameProject no encontrada"
    ALL_OK=false
fi

if grep -q 'namespace = "org.override.\$nameProject"' app/build.gradle.kts; then
    echo -e "${GREEN}✅${NC} app/build.gradle.kts contiene namespace correcto"
else
    echo -e "${RED}❌${NC} app/build.gradle.kts - namespace no encontrado"
    ALL_OK=false
fi

if grep -q 'applicationId = "org.override.\$nameProject"' app/build.gradle.kts; then
    echo -e "${GREEN}✅${NC} app/build.gradle.kts contiene applicationId correcto"
else
    echo -e "${RED}❌${NC} app/build.gradle.kts - applicationId no encontrado"
    ALL_OK=false
fi

# Verificar AndroidManifest.xml
if grep -q '@style/Theme.Tamplete' app/src/main/AndroidManifest.xml; then
    echo -e "${GREEN}✅${NC} AndroidManifest.xml contiene el tema por defecto"
else
    echo -e "${RED}❌${NC} AndroidManifest.xml - tema por defecto no encontrado"
    ALL_OK=false
fi

# Verificar strings.xml
if grep -q '<string name="app_name">Tamplete</string>' app/src/main/res/values/strings.xml; then
    echo -e "${GREEN}✅${NC} strings.xml contiene el nombre de app por defecto"
else
    echo -e "${RED}❌${NC} strings.xml - nombre de app no encontrado"
    ALL_OK=false
fi

# Verificar themes.xml
if grep -q '<style name="Theme.Tamplete"' app/src/main/res/values/themes.xml; then
    echo -e "${GREEN}✅${NC} themes.xml contiene el estilo por defecto"
else
    echo -e "${RED}❌${NC} themes.xml - estilo no encontrado"
    ALL_OK=false
fi

# Verificar estructura de packages
if [ -d "app/src/main/java/org/override/tamplete" ]; then
    echo -e "${GREEN}✅${NC} Estructura de packages por defecto existe"
else
    echo -e "${RED}❌${NC} Estructura de packages no encontrada"
    ALL_OK=false
fi

echo ""

# Verificar que el script es ejecutable
if [ -x "Scripts/init-project.sh" ]; then
    echo -e "${GREEN}✅${NC} init-project.sh es ejecutable"
else
    echo -e "${YELLOW}⚠️${NC}  init-project.sh no es ejecutable (ejecuta: chmod +x Scripts/init-project.sh)"
fi

echo ""

# Verificar que no está ya inicializado
if [ -f ".template-initialized" ]; then
    echo -e "${YELLOW}⚠️${NC}  El proyecto YA HA SIDO INICIALIZADO"
    echo -e "     Para probar el inicializador, elimina el archivo .template-initialized"
else
    echo -e "${GREEN}✅${NC} El proyecto NO ha sido inicializado (listo para usar)"
fi

echo ""
echo -e "${CYAN}═══════════════════════════════════════════════════════════════${NC}"
echo ""

if [ "$ALL_OK" = true ]; then
    echo -e "${GREEN}${CYAN}✅ VERIFICACIÓN COMPLETADA: TODO OK${NC}"
    echo ""
    echo -e "${CYAN}El template está listo para ser inicializado.${NC}"
    echo -e "Ejecuta: ${YELLOW}./Scripts/init-project.sh${NC}"
    echo ""
    exit 0
else
    echo -e "${RED}❌ VERIFICACIÓN FALLIDA: HAY PROBLEMAS${NC}"
    echo ""
    echo -e "${YELLOW}Por favor, revisa los errores arriba.${NC}"
    echo ""
    exit 1
fi

