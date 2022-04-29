#version 420

in vec3 pos;
in float out_max;
in float out_min;

out vec4 frag_color;

void main() {
    float width = 0.02f;

    //    vec3 distanceToInt = abs(pos * 4 - round(pos * 4));
    //    if (distanceToInt.x < width || distanceToInt.y < width || distanceToInt.z < width) {
    //        frag_color = vec4(0, 0, 0, 1);
    //    } else {
    //        frag_color = vec4(1, 1, 1, 1);
    //    }

    float k = pos.y - floor(pos.y);

    float red = 1 - ((pos.y - out_min) / (out_max - out_min));
    float green = ((pos.y - out_min) / (out_max - out_min));

    if (pos.y * 20 - floor(pos.y * 20) < 0.07) {
        frag_color = vec4(0.5 * red, 0.5 * green, 0, 1);
    } else {
        frag_color = vec4(red, green, 0, 1);
    }
}