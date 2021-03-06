ISO
===

Inaccurate Simulator of Orbits

A simple (and increasingly inaccurate) orbital simulator.

Starting with the reduction of Newton's Law of Universal Gravitation,

a = G ( M / r^2 )

this simulator uses an iterative approach to calculate acceleration and then velocity and position for each orbital body.

Although it can create long-lasting stable orbits if the parameters work out favorably, due to the iterative nature of the calculations, errors (such as rounding errors, or lack of precision) compound over time, hence the name.

There are methods to improve the accuracy of the calculations, but for now the goal is to add basic features and treat this as a fun toy rather than a hardcore simulation.

Feature List:

- [x] Simple Orbital Physics
- [x] Simple Input - Create bodies and place them in circular orbit around the sun
- [x] Collision detection and response between bodies
- [ ] Menu to toggle global options (collision, black hole sun, starting masses, etc)
- [x] Inter-body gravity as togglable option (currently bodies are only effected by their parent body)
- [ ] Cosmic tools - Planet canon, Debris generator, etc
- [x] Zoom (this is gross)
- [ ] Live edit for existing bodies
- [ ] More advanced collision response (shattering and fusing objects, material properties)

New Goals:
- [x] Code cleanup and refactor
- [x] Basic physics optimization
- [ ] Richocet vs. absorb for body interaction (material types?)
- [x] New body directional launch mechanic
- [x] Orbit trails
- [x] On screen tool panel

![Screenshot](/core/assets/screenshot.png)
