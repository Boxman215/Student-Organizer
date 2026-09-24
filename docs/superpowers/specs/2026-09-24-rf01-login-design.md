# RF01 Login Design

## Goal

Implement a simple, functional login with username and password for the Student Organizer project, following the existing UML and the MVC separation required by the course.

## Scope

This change covers only RF01:

- `Usuario` with `nombreUsuario`, `contrasena` and `carne`.
- File-based persistence of users through `PersistenciaDatos`.
- `ControladorLogin` for authentication and session state.
- A basic `Vista` login screen.
- Validation and executable tests for the login flow.
- Requirement and priority documentation, with empty sections for Andreh's real hours and contributions.

It does not implement grades, tasks or the general average. Those features will consume the login/session contract later.

## Design

The model stores users. `PersistenciaDatos` reads and writes a simple UTF-8 file under `data/usuarios.csv`, creating the directory and file when needed. Passwords are stored as SHA-256 hashes so the prototype does not keep plain-text passwords. The controller loads users, validates the input and stores the authenticated user in `usuarioActual`.

The view is a small Java Swing window with username, password and login controls. It calls the controller and shows a success or error message; it does not contain authentication logic.

The first run includes a non-personal demo account only when the user file is empty: `demo` / `demo123`. This makes the requirement demonstrable without inventing personal credentials. The README will explain how to replace it with real test data.

## Validation and errors

- Empty or whitespace-only username: reject with a clear message.
- Empty password: reject with a clear message.
- Unknown username or wrong password: reject without creating a session.
- Correct credentials: set `usuarioActual` and report success.
- Missing or malformed data file: create an empty valid file when possible and report persistence failures without crashing the UI.

## Verification

The tests will cover empty fields, unknown users, wrong passwords, successful authentication, logout and persistence across a new `PersistenciaDatos` instance. A manual smoke test will launch the Swing view and exercise the same success and failure paths.

## Documentation placeholders

The implementation will include a small RF01 document with placeholders for Andreh to complete with real dates, hours, interruptions, contribution links and user-feedback notes. No personal activity will be fabricated.
