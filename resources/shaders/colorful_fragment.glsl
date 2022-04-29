#version 420

in vec3 color;
in vec3 pos;

out vec4 frag_color;

void main() {
    float width = 0.02f;

    vec3 distanceToInt = abs(pos - round(pos));
    if (distanceToInt.x < width || distanceToInt.y < width || distanceToInt.z < width) {
        frag_color = vec4(0, 0, 0, 1);
    } else {
        float twoThridPi = 3.14 * 2 / 3;
        frag_color = vec4(sin(pos.y), sin(pos.y + twoThridPi), sin(pos.y - twoThridPi), 1);
    }
}