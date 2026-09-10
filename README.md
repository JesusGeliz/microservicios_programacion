# Proyecto Integrador: Sistema de Gestión - Plazoleta de Comidas

##  Descripción del Proyecto
Aplicación web diseñada para la gestión integral de una plazoleta de comidas, estructurada bajo un enfoque lógico de arquitectura de microservicios. El sistema permite la administración de usuarios, control de inventario de restaurantes y platos, trazabilidad de pedidos y notificaciones, garantizando escalabilidad e independencia funcional.

##  Arquitectura del Sistema
El proyecto se desarrolla en **Java (Programación Orientada a Objetos)**, implementando una división lógica que simula el bajo acoplamiento de microservicios independientes:

1. **Microservicio de Usuarios:** Gestión de identidades (Administrador, Propietario, Empleado, Cliente), control de acceso, validación de roles y encriptación de seguridad mediante BCrypt.
2. **Microservicio de Plazoleta:** Núcleo de negocio para la gestión de restaurantes y menús. Incluye listados paginados y reglas de validación de negocio.
3. **Microservicio de Trazabilidad:** Control del ciclo de vida de los pedidos (Pendiente, En preparación, Listo, Entregado) y asignación de empleados.
4. **Microservicio de Notificaciones:** Sistema de alertas y generación de PIN de seguridad para la entrega de pedidos.

##  Metodología y Control de Versiones
El ciclo de vida del desarrollo se gestiona mediante **Scrum-Kanban**, asegurando entregas continuas y auditables.

**Estrategia Git Flow Implementada:**
- `main`: Rama de producción, versiones estables y entregables.
- `develop`: Rama de integración de los Sprints.
- `feature/HU-XX-nombre`: Ramas efímeras para el desarrollo individual de cada Historia de Usuario.
- `release/sprint-X`: Ramas de estabilización al cierre de cada iteración.

**Nomenclatura de Commits:**
Se aplica el estándar de la convención de commits: `tipo(modulo): descripción corta [HU-xx]`
*(Tipos permitidos: feat, fix, docs, test, refactor, chore).*

##  Equipo de Desarrollo 
- **Jesús Geliz** - (Gestión de Usuarios, Seguridad y Transacciones).
- **Henao** - (Catálogo de Restaurantes y Platos).

---
*Desarrollo estructurado bajo principios de Integridad Académica. Todo el código alojado en este repositorio es de autoría propia de los miembros del equipo.*
