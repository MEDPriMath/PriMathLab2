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
        frag_color = vec4(1, 1, 1, 1);
    }
}