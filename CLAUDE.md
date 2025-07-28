# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3 application that generates landing page HTML content using OpenAI's API. The application features a modern frontend using Thymeleaf, Tailwind CSS, Alpine.js, and HTMX for dynamic interactions.

## Technology Stack

- **Backend**: Spring Boot 3.4.5, Java 17, Spring AI 1.0.0
- **Frontend**: Thymeleaf, Tailwind CSS 3.4.1, Alpine.js, HTMX
- **AI Integration**: OpenAI GPT-4o-mini via Spring AI
- **Build Tools**: Maven, npm for frontend assets
- **Styling**: Tailwind CSS with custom primary color scheme

## Development Commands

### Backend (Maven)
```bash
# Run the application
./mvnw spring-boot:run

# Build the project
./mvnw clean compile

# Run tests
./mvnw test

# Package application
./mvnw package
```

### Frontend (Tailwind CSS)
```bash
# Watch mode for development (rebuilds CSS on changes)
npm run build:css

# Production build (minified CSS)
npm run build:css:prod
```

## Architecture Overview

### Package Structure
```
com.buseni.landingpageaigenerator/
├── controller/           # REST controllers
├── service/             # Business logic interfaces
├── service/impl/        # Service implementations
└── config/              # Configuration classes
```

### Key Components

1. **AiController** (`src/main/java/.../controller/AiController.java:19-98`)
   - Main REST API endpoints for AI content generation
   - Supports both blocking and streaming responses
   - Endpoints: `/api/ai/generate-landing-page`, `/api/ai/generate-landing-page-stream`, `/api/ai/preview`

2. **AiService Interface** (`src/main/java/.../service/AiService.java:8-41`)
   - Defines contract for AI operations
   - Methods for both synchronous and reactive (Flux) content generation

3. **Frontend Template** (`src/main/resources/templates/index.html`)
   - Single-page application using Thymeleaf with Alpine.js
   - Real-time streaming content generation via Server-Sent Events
   - Responsive design with Tailwind CSS

### Configuration

- **OpenAI Configuration**: Set via `OPENAI_API_KEY` environment variable
- **Model**: Uses GPT-4o-mini with temperature 0.7, max tokens 1000
- **Internationalization**: Supports English and French via message properties
- **Actuator**: Exposes health, info, and AI endpoints

## Development Guidelines

### Backend Development (Java/Spring Boot)
Follow SOLID, DRY, KISS, and YAGNI principles as a Senior Java Developer:

#### Project Structure
- Use Maven as build tool
- Organize code into controller, service, repository, and model packages
- Use feature-driven package naming
- Use Lombok for boilerplate reduction (`@Data`, `@Builder`, `@RequiredArgsConstructor`)
- Return DTOs instead of entities in controllers
- Use records when appropriate
- Use mappers for entity/DTO conversion

#### Service Layer
- Services should be interfaces with `@Service` implementations
- Use constructor-based dependency injection
- Annotate transactional methods with `@Transactional`
- Add JavaDoc on all service interface methods

#### Controller Guidelines
- Use `@RestController` and `@RequestMapping("/api/resource")`
- Define endpoints with `@GetMapping`, `@PostMapping`, etc.
- Use `@Valid` for request validation
- Return `ResponseEntity<T>` for proper HTTP responses
- Add OpenAPI Specification to document APIs

#### Entity & Repository Rules
- Define entities with `@Entity`, `@Table(name = "table_name")`
- Use `@Id @GeneratedValue(strategy = GenerationType.IDENTITY)`
- Implement proper `@OneToMany` and `@ManyToOne` relationships
- Use `@JsonIgnore` to prevent infinite recursion
- Repositories extend `JpaRepository<Entity, Long>`
- Use `@Query` for custom queries, `@EntityGraph` to optimize joins
- Implement soft delete and auditing features when applicable

#### Exception Handling
- Implement Global Exception Handler (`@ControllerAdvice`)
- Handle ResourceNotFoundException (404), ValidationException (400), AccessDeniedException (403)

#### Security Best Practices
- Follow OWASP security guidelines
- Use Spring Security with JWT-based authentication
- Protect sensitive endpoints with `@PreAuthorize`
- Implement proper input validation and CSRF protection

### Frontend Development (Thymeleaf/Tailwind/Alpine.js/HTMX)
Act as an experienced frontend developer focusing on clean, semantic code:

#### HTML Structure & Thymeleaf
- Use Thymeleaf syntax (`th:text`, `th:if`, `th:each`) for dynamic content
- Organize components in `/templates/fragments/` using `th:replace` or `th:insert`
- Use layout fragments (header.html, footer.html, base.html)
- Use Thymeleaf layout decorator for consistent layouts
- **CRITICAL**: Always use message properties (`#{key}`) instead of hardcoded text
- Ensure all UI labels, forms, and tables use internationalized messages

#### Tailwind CSS Rules
- Use utility classes instead of custom CSS
- Support dark mode with `dark:` prefix
- Use `@apply` in styles.css for repeated patterns
- Use Flexbox (`flex`, `justify-between`) and Grid (`grid`, `gap-4`) for layouts
- Follow responsive design principles

#### Alpine.js Guidelines
- Use `x-data` for component state management
- Use `x-show`, `x-bind`, `x-on` for interactivity
- Keep logic inside `@click` or `x-init`, avoid inline JavaScript
- Use `x-transition` for smooth animations
- Maintain accessibility with ARIA attributes

#### HTMX Integration
- Use HTMX attributes (`hx-get`, `hx-post`) for AJAX requests
- Return HTML snippets from server for dynamic updates
- Use `hx-swap` attribute for DOM insertion strategies
- Implement proper error handling with appropriate HTTP status codes
- Ensure server responses work seamlessly with HTMX

#### Code Style
- Use camelCase for variables and functions
- Use PascalCase for classes and components
- Write concise, clear, and maintainable code
- Ensure clear separation between frontend templates and backend controllers

### Streaming Implementation
- AI content generation uses Server-Sent Events (SSE) for real-time updates
- Frontend handles streaming via EventSource API
- Proper error handling and connection cleanup implemented

### Security Considerations
- Content Security Policy headers configured for preview functionality
- Input validation on all API endpoints
- No sensitive information in code or templates

## Testing

The project uses standard Spring Boot testing conventions:
- Test classes in `src/test/java/com/buseni/landingpage_aigenerator/`
- Use `@SpringBootTest` for integration tests
- Follow naming convention: `*Tests.java`

## Styling and UI

### Tailwind Configuration
- Custom primary color palette (blue-based)
- Typography plugin enabled
- Forms plugin enabled
- Dark mode support via `class` strategy
- Content paths: `./src/main/resources/templates/**/*.html`

### Design System
- Uses Inter font family
- Primary colors: Blue-based palette (50-900 shades)
- Responsive breakpoints following Tailwind defaults
- Dark mode toggle support built-in

## Environment Variables

Required environment variables:
- `OPENAI_API_KEY`: OpenAI API key for content generation

## Deployment

The project includes Jib Maven plugin for containerization and GitHub Actions workflow for Google Cloud Run deployment.